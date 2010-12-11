package com.dominikdorn.jrr.servlet;

import com.dominikdorn.jrr.core.Constants;
import com.dominikdorn.jrr.core.domain.Relocator;
import com.dominikdorn.jrr.core.domain.RelocatorFactory;
import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.exceptions.ConfigurationIOException;
import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import com.dominikdorn.jrr.jsf.PrimitiveResourceResolver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * This ServletListener initializes the JSF Resource Resolver and
 * registers the objects in the ServletContext.
 */
public class JRRServletListener implements ServletContextListener {

    Logger log = Logger.getLogger("jsf-resource-relocator");



    public void contextInitialized(ServletContextEvent servletContextEvent) {
        URL configurationURL = null;
        try{
            configurationURL = servletContextEvent.getServletContext().getResource(Constants.CONFIGURATION_FILE);
            Relocator relocator = RelocatorFactory.getRelocator(configurationURL);
            PrimitiveResourceResolver resourceResolver = new PrimitiveResourceResolver(relocator);

            servletContextEvent.getServletContext().setAttribute(Constants.CONTEXT_RELOCATOR_ATTRIBUTE_NAME, relocator);
            servletContextEvent.getServletContext().setAttribute(Constants.CONTEXT_RESOURCE_RESOLVER_ATTRIBUTE_NAME, resourceResolver);
        }
        catch (ConfigurationIOException e) {
            log.warning("Failed to load configuration file.");
            e.printStackTrace();
        } catch (ConfigurationParsingException e) {
            log.warning("Failed to parse the Configuration file " + Constants.CONFIGURATION_FILE + "" +
                    "Please check the syntax using the XSD."
            );
        } catch (ConfigurationNotFoundException e) {
            log.warning("JSF Resource Relocator not loaded: Configuration file WEB-INF/jsf-resource-relocator.xml is missing.");
        }
        catch (ConfigurationException e)
        {
            log.info("Catched a general ConfigurationException. ");
        } catch (MalformedURLException e) {
            throw new IllegalStateException("This should never happen, " +
                    "as the URL to the configuration file is hardcoded into the source.", e);
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute(Constants.CONTEXT_RELOCATOR_ATTRIBUTE_NAME);
        servletContextEvent.getServletContext().removeAttribute(Constants.CONTEXT_RESOURCE_RESOLVER_ATTRIBUTE_NAME);
    }
}
