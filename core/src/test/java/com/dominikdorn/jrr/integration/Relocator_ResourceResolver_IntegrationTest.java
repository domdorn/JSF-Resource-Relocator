package com.dominikdorn.jrr.integration;

import com.dominikdorn.jrr.core.ResolverResult;
import com.dominikdorn.jrr.core.domain.Relocator;
import com.dominikdorn.jrr.core.domain.RelocatorFactory;
import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.jsf.PrimitiveResourceResolver;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Relocator_ResourceResolver_IntegrationTest {

    private static final String FILE_JSF_RESOURCE_RELOCATOR_1_XML = "integration/jsf-resource-relocator_1.xml";
    private static final String FILE_JSF_RESOURCE_RELOCATOR_2_XML = "integration/jsf-resource-relocator_2.xml";
    Relocator relocator;
    PrimitiveResourceResolver resourceResolver;

    public void prepare(String path) throws ConfigurationException {
        URL urlURL =  ClassLoader.getSystemResource(path);
        relocator = RelocatorFactory.getRelocator(urlURL);
        resourceResolver = new PrimitiveResourceResolver(relocator);
    }


    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void registered_relocated_resource() throws ConfigurationException {
        prepare(FILE_JSF_RESOURCE_RELOCATOR_1_XML);

        ResolverResult result = resourceResolver.getResourceURL("primefaces", "jquery/jquery.js");
        String url = result.getUrl();
        assertNotNull(url);
        assertEquals("http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js", url);
    }

    @Test
    public void notRegistered_resource_mirrored() throws ConfigurationException {
        prepare(FILE_JSF_RESOURCE_RELOCATOR_1_XML);

        ResolverResult result = resourceResolver.getResourceURL("primefaces", "jquery/lightbox.js");
        assertNotNull(result);
        assertNotNull(result.getUrl());
        assertEquals(ResolverResult.TYPE.ABSOLUTE, result.getType());
        assertEquals("http://static.studyguru.net/resources/primefaces/jquery/lightbox.js", result.getUrl());
    }

    @Test
    public void notRegistered_resource_not_mirrored() throws ConfigurationException {
        prepare(FILE_JSF_RESOURCE_RELOCATOR_1_XML);

        ResolverResult result = resourceResolver.getResourceURL("prettyfaces", "jquery/lightbox.js");
        String url = result.getUrl();
        assertNull(url);
    }

    @Test
    public void single_resource_in_library() throws ConfigurationException {
        prepare(FILE_JSF_RESOURCE_RELOCATOR_2_XML);

        ResolverResult result = resourceResolver.getResourceURL("primefaces", "jquery/jquery.js");
        assertNotNull(result);
        assertNotNull(result.getUrl());
        assertEquals(ResolverResult.TYPE.ABSOLUTE, result.getType());
        assertEquals("http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js", result.getUrl());
    }

    @Test
    public void resource_in_lib_without_mirror() throws ConfigurationException {
        prepare(FILE_JSF_RESOURCE_RELOCATOR_2_XML);

        ResolverResult result = resourceResolver.getResourceURL("primefaces", "jquery/lightbox.js");
        assertNotNull(result);
        assertNull(result.getUrl());
        assertEquals(ResolverResult.TYPE.NOT_MANAGED, result.getType());
//        assertEquals("http://static.studyguru.net/resources/primefaces/jquery/lightbox.js", result.getUrl());
    }
}
