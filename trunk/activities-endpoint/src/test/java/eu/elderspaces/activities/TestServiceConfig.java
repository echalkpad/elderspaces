package eu.elderspaces.activities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import eu.elderspaces.activities.services.TestJerseyServletModule;

import javax.servlet.ServletContextEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestServiceConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new TestJerseyServletModule()
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}