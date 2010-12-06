package com.dominikdorn.jrr.core;

import com.dominikdorn.jrr.exceptions.ConfigurationNotFoundException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        RelocatorFactory.getRelocator(null);
    }


    @Test(expected = ConfigurationParsingException.class)
    public void emptyFileInputStream() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_INVALID_EMPTY_FILE);
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);
        RelocatorFactory.getRelocator(input);
    }

    @Test
    public void emptyRelocatorConfigration() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_EMPTY_CONFIG);
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
        assertNotNull(relocator);
    }

    @Test
    public void two_empty_libraries() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_SINGLE_LIBRARY_FILE);
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
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
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
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
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
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
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
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
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
        assertNotNull(relocator);
        assertNotNull(relocator.update);
        assertEquals(UPDATE_DISABLED, relocator.update.enabled);
        assertEquals(UPDATE_DEFAULT_USERNAME, relocator.update.user);
        assertEquals(UPDATE_DEFAULT_PASSWORD, relocator.update.pass);
    }
}
