package com.dominikdorn.jrr.jsf;

import javax.faces.application.Resource;
import javax.faces.application.ResourceWrapper;

/**
 * Wrapper around a <em>Resource</em> returning
 * our own calculated URL when asked
 */
public class JRRResourceWrapper extends ResourceWrapper {

    private final Resource resource;
    private final String absoluteUrl;

    public JRRResourceWrapper(
            final Resource resource,
            final String absoluteUrl) {
        this.resource = resource;
        this.absoluteUrl = absoluteUrl;
    }

    @Override
    public String getRequestPath() {
        return absoluteUrl;
    }

    @Override
    public Resource getWrapped() {
        return resource;
    }
}
