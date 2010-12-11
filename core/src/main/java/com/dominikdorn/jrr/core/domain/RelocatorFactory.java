package com.dominikdorn.jrr.core.domain;

import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.exceptions.ConfigurationIOException;
import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RelocatorFactory {

    public static Relocator getRelocator(URL input) throws ConfigurationException
    {
        if(input == null)
            throw new ConfigurationNotFoundException();

        try {
            validateConfigurationFile(input.openStream());

            return getRelocator(input.openStream());
        } catch (FileNotFoundException e) {
            throw new ConfigurationNotFoundException();
        } catch (IOException e) {
            throw new ConfigurationNotFoundException();
        }
    }

    private static void validateConfigurationFile(InputStream input) throws ConfigurationException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = null;
        InputSource inputSource;
        try {
            schema = sf.newSchema(
                new File(Relocator.class.getResource("/META-INF/jsf_resource_resolver_1_0.xsd").getFile()));
            Validator val = schema.newValidator();

            inputSource = new InputSource(input);
            SAXSource source = new SAXSource(inputSource);
            val.validate(source);
        } catch (SAXException e) {
            throw new ConfigurationParsingException(e);
        } catch (IOException e) {
            throw new ConfigurationIOException(e);
        }
        finally {
            if(input != null)
            {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static Relocator getRelocator(InputStream input) throws
            ConfigurationException {
        if(input == null)
            throw new ConfigurationNotFoundException();
        Relocator relocator;
        Digester digester = new Digester();
        digester.setValidating(false);

        digester.setUseContextClassLoader(true);
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

        digester.addObjectCreate("relocator/mirrors/mirror/for", MirrorFor.class.getCanonicalName());
        digester.addSetProperties("relocator/mirrors/mirror/for");
        digester.addSetNext("relocator/mirrors/mirror/for", "addLibrary", MirrorFor.class.getCanonicalName());

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
