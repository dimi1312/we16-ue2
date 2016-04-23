package at.ac.tuwien.big.we16.ue2.listener;

import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by vanessa on 22.04.16.
 */
@WebListener
public class Cleaner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactory.getNotifierService().stop();
    }
}
