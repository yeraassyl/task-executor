package kz.kaspi.lab.rest;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class AppListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }
}
