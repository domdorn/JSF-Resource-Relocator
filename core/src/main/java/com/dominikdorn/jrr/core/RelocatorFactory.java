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
            ConfigurationException {
        if(input == null)
            throw new ConfigurationNotFoundException();
        Relocator relocator;
        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);
        digester.setValidating(false);
        digester.addObjectCreate("relocator", Relocator.class.getCanonicalName());

        digester.addObjectCreate("relocator/libraries/library", Library.class.getCanonicalName());
        digester.addSetProperties("relocator/libraries/library");
        digester.addSetNext("relocator/libraries/library", "addLibrary", Library.class.getCanonicalName());


        digester.addObjectCreate("relocator/libraries/library/exclude", ExcludeLibraryEntry.class.getCanonicalName());
        digester.addSetProperties("relocator/libraries/library/exclude");
        digester.addSetNext("relocator/libraries/library/exclude", "addEntry", ExcludeLibraryEntry.class.getCanonicalName());

        digester.addObjectCreate("relocator/libraries/library/replace", ReplaceLibraryEntry.class.getCanonicalName());
        digester.addSetProperties("relocator/libraries/library/replace");
        digester.addSetNext("relocator/libraries/library/replace", "addEntry", ReplaceLibraryEntry.class.getCanonicalName());

        digester.addObjectCreate("relocator/libraries/library/resource", ResourceLibraryEntry.class.getCanonicalName());
        digester.addSetProperties("relocator/libraries/library/resource");
        digester.addSetNext("relocator/libraries/library/resource", "addEntry", ResourceLibraryEntry.class.getCanonicalName());


        digester.addObjectCreate("relocator/mirrors/mirror", Mirror.class.getCanonicalName());
        digester.addSetProperties("relocator/mirrors/mirror");
        digester.addSetNext("relocator/mirrors/mirror", "addMirror", Mirror.class.getCanonicalName());

        digester.addObjectCreate("relocator/update", Update.class.getCanonicalName());
        digester.addSetProperties("relocator/update");

        digester.addBeanPropertySetter("relocator/update/user", "user");
        digester.addBeanPropertySetter("relocator/update/pass", "pass");

        digester.addSetRoot("relocator/update", "setUpdate", Update.class.getCanonicalName());

        try {
            relocator = (Relocator) digester.parse(input);
            relocator.checkIntegrity();
            return relocator;
        } catch (IOException e) {
            throw new ConfigurationIOException(e);
        } catch (SAXException e) {
            throw new ConfigurationParsingException(e);
        }
    }
}
