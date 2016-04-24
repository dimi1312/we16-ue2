package at.ac.tuwien.big.we16.ue2.listener;

import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The class implements a ServletContextListener.
 * Created by vanessa on 22.04.16.
 */
@WebListener
public class Cleaner implements ServletContextListener {
    /**
     * Precondition: servletContextEvent != null
     * Postcondition: The methode is called after the initialisation of the ServletContext. The current sortiment
     * is created and stored in the ServletContext for future processing. Then the AuctionWatcher and the ComputerUser are
     * startet.
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Sortiment sortiment = new Sortiment();
        ServiceFactory.getNotifierService().setSortiment(sortiment);
        servletContextEvent.getServletContext().setAttribute("sortiment", sortiment);
        ServiceFactory.getNotifierService().startAuctionEndWatcher();
        ServiceFactory.getNotifierService().startComputerUser();
    }

    /**
     * Precondition: servletContextEvent != null
     * Postcondition: The methode is called after the ServletContext is destroyed. The Execution of
     * the threads in the NotifierService (computerUser and auctionEndWatcher) are stopped.
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactory.getNotifierService().stop();
    }
}
