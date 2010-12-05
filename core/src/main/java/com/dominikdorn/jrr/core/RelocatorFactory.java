package com.dominikdorn.jrr.core;

import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.exceptions.ConfigurationIOException;
import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RelocatorFactory {

    public static Relocator getRelocator(InputStream input) throws
            ConfigurationParsingException, ConfigurationIOException, ConfigurationNotFoundException {
        if(input == null)
            throw new ConfigurationNotFoundException();

        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);
        digester.setValidating(false);
        digester.addObjectCreate("relocator", Relocator.class);


//                digester.addObjectCreate("exception-handler", com.dominikdorn.sk.jsf.eh.ExceptionHandler.class.getCanonicalName());
//                digester.addSetProperties("exception-handler");
//                digester.addObjectCreate("exception-handler/exception", ExceptionMapping.class.getCanonicalName());
//                digester.addSetProperties("exception-handler/exception");
//                digester.addSetNext("exception-handler/exception", "addMapping", ExceptionMapping.class.getCanonicalName());
        try {
            Relocator relocator = (Relocator) digester.parse(input);
        } catch (IOException e) {
            throw new ConfigurationIOException(e);
        } catch (SAXException e) {
            throw new ConfigurationParsingException(e);
        }
//                this.eh = (com.dominikdorn.sk.jsf.eh.ExceptionHandler) digester.parse(input);


        return new Relocator();
    }
}
