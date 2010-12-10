package com.dominikdorn.jrr.core;

import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RelocatorFactoryTest {

    private final String EXAMPLE_CONFIGURATION_01 = "config_files/jsf-resource-relocator_example_01.xml";
    private final String EXAMPLE_CONFIGURATION_EMPTY_CONFIG = "config_files/empty_config.xml";
    private final String EXAMPLE_CONFIGURATION_INVALID_EMPTY_FILE = "config_files/invalid_config.xml";
    private final String EXAMPLE_CONFIGURATION_SINGLE_LIBRARY_FILE = "config_files/single_library.xml";
    private final String EXAMPLE_CONFIGURATION_TWO_EMPTY_MIRRORS_FILE = "config_files/two_empty_mirrors.xml";
    private final String EXAMPLE_CONFIGURATION_UPDATE_ENABLED_FILE = "config_files/update_enabled.xml";
    private final String EXAMPLE_CONFIGURATION_UPDATE_DISABLED_FILE = "config_files/update_disabled.xml";
    private final String EXAMPLE_CONFIGURATION_UPDATE_ADMIN_DEFAULT_CREDENTIALS_DISABLED_FILE = "config_files/update_admin_default_cred_disabled.xml";

    private final String EXAMPLE_CONFIGURATION_LIBRARY_EXCLUDE_RESOURCES_FILE = "config_files/exclude_resource.xml";
    private final String EXAMPLE_CONFIGURATION_LIBRARY_REPLACE_RESOURCES_FILE = "config_files/replace_resource.xml";
    private final String EXAMPLE_CONFIGURATION_LIBRARY_DECLARE_RESOURCES_FILE = "config_files/declare_resource.xml";


    private final String UPDATE_USERNAME = "domdorn";
    private final String UPDATE_PASSWORD = "testpassword";

    private final String UPDATE_DEFAULT_USERNAME = "admin";
    private final String UPDATE_DEFAULT_PASSWORD = "default";

    private final boolean UPDATE_ENABLED = true;
    private final boolean UPDATE_DISABLED = false;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test(expected = ConfigurationNotFoundException.class)
    public void nullInputStream() throws Exception
    {
        RelocatorFactory.getRelocator((URL)null);
    }


    @Test(expected = ConfigurationParsingException.class)
    public void emptyFileInputStream() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_INVALID_EMPTY_FILE);
        RelocatorFactory.getRelocator(urlURL);
    }

    @Test
    public void emptyRelocatorConfigration() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_EMPTY_CONFIG);
        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
    }

    @Test
    public void two_empty_libraries() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_SINGLE_LIBRARY_FILE);
        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.libraries);
        assertEquals(2, relocator.libraries.size());
        assertEquals("primefaces", relocator.libraries.get(0).id);
        assertEquals("prettyfaces", relocator.libraries.get(1).id);
    }

    @Test
    public void two_empty_mirrors() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_TWO_EMPTY_MIRRORS_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.mirrors);
        assertEquals(2, relocator.mirrors.size());
        assertEquals("hetzner", relocator.mirrors.get(0).id);
        assertEquals("aws_s3", relocator.mirrors.get(1).id);
    }
    
    @Test
    public void update_enabled() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_UPDATE_ENABLED_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.update);
        assertEquals(UPDATE_ENABLED, relocator.update.enabled);
        assertEquals(UPDATE_USERNAME, relocator.update.user);
        assertEquals(UPDATE_PASSWORD, relocator.update.pass);
    }

    @Test
    public void update_disabled() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_UPDATE_DISABLED_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.update);
        assertEquals(UPDATE_DISABLED, relocator.update.enabled);
        assertEquals(UPDATE_USERNAME, relocator.update.user);
        assertEquals(UPDATE_PASSWORD, relocator.update.pass);
    }

    @Test
    public void update_default_credentials_disabled() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_UPDATE_ADMIN_DEFAULT_CREDENTIALS_DISABLED_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.update);
        assertEquals(UPDATE_DISABLED, relocator.update.enabled);
        assertEquals(UPDATE_DEFAULT_USERNAME, relocator.update.user);
        assertEquals(UPDATE_DEFAULT_PASSWORD, relocator.update.pass);
    }

    @Test
    public void library_exclude_resources() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_LIBRARY_EXCLUDE_RESOURCES_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.libraries);
        assertEquals(2, relocator.libraries.size());
        assertEquals("primefaces", relocator.libraries.get(0).id);
        assertEquals(2, relocator.libraries.get(0).getEntries().size());
        assertEquals("jquery/jquery.js", relocator.libraries.get(0).getEntries().get(0).getName());
        assertEquals("jquery/jquery-lightbox.js", relocator.libraries.get(0).getEntries().get(1).getName());
    }

    @Test
    public void library_replace_resources() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_LIBRARY_REPLACE_RESOURCES_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.libraries);
        assertEquals(3, relocator.libraries.size());
        assertEquals("primefaces", relocator.libraries.get(0).id);
        assertEquals(1, relocator.libraries.get(0).getEntries().size());
        assertEquals("jquery/jquery.js", relocator.libraries.get(0).getEntries().get(0).getName());
        assertEquals(ReplaceLibraryEntry.class, relocator.libraries.get(0).getEntries().get(0).getClass());
        assertEquals("google_cdn_jquery_1_4", ((ReplaceLibraryEntry) relocator.libraries.get(0).getEntries().get(0)).getWith());
    }

    @Test
    public void library_declare_resources() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_LIBRARY_DECLARE_RESOURCES_FILE);

        Relocator relocator = RelocatorFactory.getRelocator(urlURL);
        assertNotNull(relocator);
        assertNotNull(relocator.libraries);
        assertEquals(3, relocator.libraries.size());
        assertEquals("primefaces", relocator.libraries.get(0).id);
        assertEquals(0, relocator.libraries.get(0).getEntries().size());
        assertEquals(0, relocator.libraries.get(1).getEntries().size());
        assertEquals(1, relocator.libraries.get(2).getEntries().size());
        assertEquals(ResourceLibraryEntry.class, relocator.libraries.get(2).getEntries().get(0).getClass());
        ResourceLibraryEntry entry = ((ResourceLibraryEntry) relocator.libraries.get(2).getEntries().get(0));
        assertEquals("google_cdn_jquery_1_4", entry.getId());
        assertEquals("jquery/jquery.js", entry.getName());
        assertEquals("http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js", entry.getAbsoluteUrl());

    }
}
