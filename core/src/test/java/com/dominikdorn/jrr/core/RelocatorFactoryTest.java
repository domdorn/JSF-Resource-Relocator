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
    public void singleLibrary() throws Exception
    {
        URL urlURL =  ClassLoader.getSystemResource(EXAMPLE_CONFIGURATION_SINGLE_LIBRARY_FILE);
        InputStream input = new FileInputStream(urlURL.getFile());
        assertNotNull(input);

        Relocator relocator = RelocatorFactory.getRelocator(input);
        assertNotNull(relocator);
        System.out.println("relocator: "+relocator);
        assertNotNull(relocator.libraries);
        assertEquals(2, relocator.libraries.size());
        assertEquals("primefaces", relocator.libraries.get(0).id);
        assertEquals("prettyfaces", relocator.libraries.get(1).id);
    }

}
