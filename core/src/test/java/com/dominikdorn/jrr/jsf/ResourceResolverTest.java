package com.dominikdorn.jrr.jsf;

import com.dominikdorn.jrr.core.ResolverResult;
import com.dominikdorn.jrr.core.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ResourceResolverTest {

    private final String GOOGLE_JQUERY_14 = "http://cdn.google.com/jquery/jquery_1.4-min.js";

    Relocator relocator1;

    PrimitiveResourceResolver resolver1;

    Library primeFaces;
    Library prettyFaces;

    Library googleJQuery;
    Library dominikJQuery;

    ResourceLibraryEntry google_jquery_14;
    ReplaceLibraryEntry primefaces_jquery;
    ResourceLibraryEntry dominik_jquery_15;


    Mirror hetzner;
    Mirror aws_s3;

    @Before
    public void setUp() throws Exception {
        relocator1 = new Relocator();
        /* libraries */
        primeFaces = new Library();
        primeFaces.setId("primefaces");

        prettyFaces = new Library();
        prettyFaces.setId("prettyfaces");

        googleJQuery = new Library();
        googleJQuery.setId("google_jquery");

        dominikJQuery = new Library();
        dominikJQuery.setId("dominik_jquery");


        /* resources */
        google_jquery_14 = new ResourceLibraryEntry();
        google_jquery_14.setId("google_jquery_14");
        google_jquery_14.setName("jquery/jquery_1.4-min.js");
        google_jquery_14.setAbsoluteUrl(GOOGLE_JQUERY_14);

        primefaces_jquery = new ReplaceLibraryEntry();
        primefaces_jquery.setName("jquery/jquery.js");
        primefaces_jquery.setWith("google_jquery_14");


        dominik_jquery_15 = new ResourceLibraryEntry();
        dominik_jquery_15.setId("dominik_jquery_15");
        dominik_jquery_15.setName("jquery/jquery_15.js");


        /* mapping resources to libraries */

        googleJQuery.addEntry(google_jquery_14);
        primeFaces.addEntry(primefaces_jquery);
        dominikJQuery.addEntry(dominik_jquery_15);

        relocator1.addLibrary(googleJQuery);
        relocator1.addLibrary(primeFaces);
        relocator1.addLibrary(dominikJQuery);

        /* Mirrors */
        hetzner = new Mirror();
        hetzner.setId("hetzner");
        hetzner.setBasePath("http://static.studyguru.net/resources/");

        aws_s3 = new Mirror();
        aws_s3.setId("aws_s3");
        aws_s3.setBasePath("http://aws.studyguru.net/resources/");

        relocator1.addMirror(hetzner);
        relocator1.addMirror(aws_s3);

        /* creating the ResourceResolver */
        resolver1 = new PrimitiveResourceResolver(relocator1);
    }

    @Test(expected = NullPointerException.class)
    public void getResourceURL_null_NPE_expected() {
        resolver1.getResourceURL(null, null);
        fail();
    }

    @Test
    public void getResourceURL_notManagedLibrary() {
        ResolverResult result = resolver1.getResourceURL("unknowLibrary", "jquery/jquery.js");
        assertNotNull(result);
        assertEquals(ResolverResult.TYPE.NOT_MANAGED, result.getType());
    }

    @Test
    public void getResourceURL_managedLibrary_unknownResource_mirrored() {
        hetzner.addLibrary("primefaces");

        ResolverResult result = resolver1.getResourceURL("primefaces", "jquery/lightbox.js");
        String url = result.getUrl();

        assertNotNull(url);
        Assert.assertEquals(hetzner.getBasePath() + "/primefaces/" + "jquery/lightbox.js", url);
    }

    @Test
    public void getResourceURL_managedLibrary_unknownResource_notMirrored() {
        ResolverResult result = resolver1.getResourceURL("primefaces", "jquery/lightbox.js");
        String url = result.getUrl();

        assertNull(url);
    }

    @Test
    public void getResourceURL_managedLibrary_replaceResource() {

        ResolverResult result = resolver1.getResourceURL("primefaces", "jquery/jquery.js");
        String url = result.getUrl();

        assertNotNull(url);
        assertEquals(GOOGLE_JQUERY_14, url);
    }

    @Test
    public void getResourceURL_managedLibrary_replaceResource_otherManagedResource() {
        hetzner.addLibrary("dominik_jquery");
        primefaces_jquery.setWith("dominik_jquery_15");
        ResolverResult result = resolver1.getResourceURL("primefaces", "jquery/jquery.js");
        String url = result.getUrl();
        assertNotNull(url);
        assertEquals(hetzner.getBasePath() + "/" + dominikJQuery.getId() + "/" + dominik_jquery_15.getName(), url);
    }

    @After
    public void tearDown() throws Exception {

    }
}
