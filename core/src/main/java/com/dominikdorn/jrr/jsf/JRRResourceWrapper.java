package com.dominikdorn.jrr.jsf;

import javax.faces.application.Resource;
import javax.faces.application.ResourceWrapper;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class JRRResourceWrapper extends ResourceWrapper {

    private final Resource resource;
    private final String baseUrl;

    public JRRResourceWrapper(final Resource resource, final String baseUrl) {
        this.resource = resource;
        this.baseUrl = baseUrl;
    }

    @Override
    public String getRequestPath() {
        return baseUrl + resource.getResourceName();
//        String parentRequestPath = super.getRequestPath();
//        System.out.println("parentRequestPath = " + parentRequestPath);
//        System.out.println("resource.getLibraryName() = " + resource.getLibraryName());
//        System.out.println("resource.getResourceName() = " + resource.getResourceName());
//        return parentRequestPath;
    }

    @Override
    public Resource getWrapped() {
        return resource;
    }
}
