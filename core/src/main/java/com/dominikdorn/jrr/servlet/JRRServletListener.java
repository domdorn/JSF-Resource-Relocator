package com.dominikdorn.jrr.servlet;

import com.dominikdorn.jrr.core.Constants;
import com.dominikdorn.jrr.core.Relocator;
import com.dominikdorn.jrr.core.RelocatorFactory;
import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.exceptions.ConfigurationIOException;
import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.apache.commons.digester.Digester;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JRRServletListener implements ServletContextListener {

    Logger log = Logger.getLogger("jsf-resource-relocator");



    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InputStream inputStream = null;
        try{

            inputStream = servletContextEvent.getServletContext().getResourceAsStream(Constants.CONFIGURATION_FILE);
            Relocator relocator = RelocatorFactory.getRelocator(inputStream);
            servletContextEvent.getServletContext().setAttribute(Constants.CONTEXT_ATTRIBUTE_NAME, relocator);
        }
        catch (ConfigurationIOException e) {
            log.warning("Failed to load configuration file.");
            e.printStackTrace();
        } catch (ConfigurationParsingException e) {
            log.warning("Failed to parse the Configuration file " + Constants.CONFIGURATION_FILE + "" +
                    "Please check the syntax using the XSD"
            );
        } catch (ConfigurationNotFoundException e) {
            log.info("JSF Resource Relocator not loaded: Configuration file WEB-INF/jsf-resource-relocator.xml is missing.");
        }
        catch (ConfigurationException e)
        {
            log.info("Catched a general ConfigurationException. ");
        }
        finally {
            if(inputStream != null)
            {
                try{
                    inputStream.close();
                } catch (IOException e) {
                    // shouldn't happen
                    log.warning("Failed to close the inputStream. This shouldn't happen");
                }
            }
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute(Constants.CONTEXT_ATTRIBUTE_NAME);
    }
}
