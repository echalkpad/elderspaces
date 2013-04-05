package eu.elderspaces.activities.persistence;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class ElasticSearchActivityRepositoryTestCase extends AbstractActivityRepositoryTestCase {
    
    @Override
    protected void specificImplementationInitialize() {
    
        activityRepository = new ElasticSearchActivityRepository();
        LOGGER = LoggerFactory.getLogger(ElasticSearchActivityRepositoryTestCase.class);
        
    }
    
    @Override
    protected void specificImplementationShutDown() {
    
        activityRepository.shutDownRepository();
        
    }
}
