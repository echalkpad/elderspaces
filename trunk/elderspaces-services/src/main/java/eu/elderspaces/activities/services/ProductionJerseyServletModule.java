package eu.elderspaces.activities.services;

import it.cybion.commons.PropertiesHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.activities.core.ActivityStreamManager;
import eu.elderspaces.activities.core.MultiLayerActivityStreamManager;
import eu.elderspaces.model.utils.ActivityStreamObjectMapper;
import eu.elderspaces.persistence.ActivityStreamRepository;
import eu.elderspaces.persistence.BluePrintsSocialNetworkRepository;
import eu.elderspaces.persistence.ElasticSearchActivityStreamRepository;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.LuceneEntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ProductionJerseyServletModule extends JerseyServletModule {
    
    public Properties properties;
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductionJerseyServletModule.class);
    
    @Override
    protected void configureServlets() {
    
        LOGGER.debug("configuring servlets");
        
        final Map<String, String> initParams = new HashMap<String, String>();
        
        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
                ClasspathResourceConfig.class.getName());
        
        final Properties properties = PropertiesHelper
                .readFromClasspath("/activities-endpoint.properties");
        // binds the keynames as @Named annotations
        Names.bindProperties(binder(), properties);
        this.properties = properties;
        
        // add bindings of backend classes
        
        // bind services classes
        bind(StatusService.class);
        bind(ActivitiesService.class);
        
        bind(Node.class).asEagerSingleton();
        bind(Client.class);
        bind(ActivityStreamRepository.class).to(ElasticSearchActivityStreamRepository.class);
        
        bind(Neo4jGraph.class).asEagerSingleton();
        bind(SocialNetworkRepository.class).to(BluePrintsSocialNetworkRepository.class);
        
        try {
            bind(Directory.class).toInstance(
                    new SimpleFSDirectory(new File((String) properties
                            .get("eu.elderspaces.repository.entities"))));
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bind(Analyzer.class).toInstance(new WhitespaceAnalyzer(Version.LUCENE_36));
        bind(EntitiesRepository.class).to(LuceneEntitiesRepository.class);
        
        bind(ActivityStreamManager.class).to(MultiLayerActivityStreamManager.class);
        
        // add bindings for Jackson
        bind(JacksonJaxbJsonProvider.class).asEagerSingleton();
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        // Route all requests through GuiceContainer
        serve("/rest/*").with(GuiceContainer.class);
        filter("/rest/*").through(GuiceContainer.class, initParams);
        LOGGER.debug("configured servlets");
    }
    
    @Provides
    public ObjectMapper objectMapperProvider() {
    
        final ObjectMapper mapper = ActivityStreamObjectMapper.getDefaultMapper();
        mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        return mapper;
    }
    
    @Provides
    private Node initElasticSearchNode(
            @Named("eu.elderspaces.repository.elasticsearch.cluster") final String clusterName,
            @Named("eu.elderspaces.repository.activity-streams") final String dataDirectory) {
    
        final ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder()
                .put("http.enabled", "false").put("path.data", dataDirectory);
        
        final Node node = NodeBuilder.nodeBuilder().local(true).clusterName(clusterName)
                .settings(elasticsearchSettings.build()).node();
        return node;
    }
    
    @Provides
    Neo4jGraph neo4jGraphProvider(
            @Named("eu.elderspaces.repository.social-network") final String socialNetworkRepository) {
    
        return new Neo4jGraph(socialNetworkRepository);
    }
}
