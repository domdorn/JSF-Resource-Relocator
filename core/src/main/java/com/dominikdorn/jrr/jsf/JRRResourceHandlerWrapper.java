package com.dominikdorn.jrr.jsf;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JRRResourceHandlerWrapper extends ResourceHandlerWrapper {

    ResourceHandler parent;

    Map<String,String> baseUrls;


    public JRRResourceHandlerWrapper(ResourceHandler parent) {
        this.parent = parent;
        baseUrls = new HashMap<String,String>();

        baseUrls.put("primefaces", "http://static.studyguru.net/res/pf/");


        System.out.println("creating SkResourceHandlerWrapper");
    }

    @Override
    public ResourceHandler getWrapped() {
        return parent;
    }

    @Override
    public Resource createResource(String resourceName) {
        Resource r = super.createResource(resourceName);
//        System.out.println("Resource createResource(String resourceName) = (" + resourceName + ")");
        return r;
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        Resource r = super.createResource(resourceName, libraryName);
        if(baseUrls.containsKey(libraryName))
            return new JRRResourceWrapper(r, baseUrls.get(libraryName));
//        System.out.println("Resource createResource(String resourceName, String libraryName) = (" + resourceName +"," + libraryName + ")");
        return r;
    }

    @Override
    public Resource createResource(String resourceName, String libraryName, String contentType) {
        Resource r = super.createResource(resourceName, libraryName, contentType);
//        System.out.println("Resource createResource(String resourceName, String libraryName, String contentType) = (" + resourceName +"," + libraryName + "," + contentType + ")");
        return r;
    }

    @Override
    public boolean libraryExists(String libraryName) {
        boolean result = super.libraryExists(libraryName);

//        System.out.println("library " + libraryName + " does " + (result?"not":"") + " exist");

        return result;
    }
}
