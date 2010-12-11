package com.dominikdorn.jrr.jsf;

import com.dominikdorn.jrr.core.Constants;
import com.dominikdorn.jrr.core.ResolverResult;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.logging.Logger;

/**
 * This class is a Wrapper around a ResourceHandler.
 * JSF creates this wrapper at application startup and
 * every time a resource needs to be rendered.
 */
public final class JRRResourceHandlerWrapper extends ResourceHandlerWrapper {
    Logger log = Logger.getLogger("JRRResourceHandlerWrapper");

    ResourceHandler parent;
    ServletContext ctx;
    private PrimitiveResourceResolver resourceResolver;


    public JRRResourceHandlerWrapper(ResourceHandler parent) {
        log.fine("invoking JRRResourceHandlerWrapper constructor");
        this.parent = parent;
        ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        log.fine("got servletcontext : " + ctx);
    }

    @Override
    public ResourceHandler getWrapped() {
        return parent;
    }

    @Override
    public Resource createResource(String resourceName) {
        log.fine("calling createResource(" + resourceName + ")");
        return getWrapped().createResource(resourceName);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        log.fine("calling createResource(" + resourceName + ", " + libraryName + ")");
        if(getResourceResolver() == null)
        {
            log.fine("ResourceResolver is NULL!!!!");
            return getWrapped().createResource(resourceName, libraryName);
        }
        ResolverResult result = getResourceResolver().getResourceURL(libraryName, resourceName);
        if(result.getType().equals(ResolverResult.TYPE.NOT_MANAGED))
        {
            return getWrapped().createResource(resourceName, libraryName);
        }
        if(result.getType().equals(ResolverResult.TYPE.EXCLUDED))
        {
            return null;
        }
        if(result.getType().equals(ResolverResult.TYPE.ABSOLUTE))
        {
            return new JRRResourceWrapper(getWrapped().createResource(resourceName, libraryName), result.getUrl());
        }
        throw new IllegalStateException("We should never be here anyway.");
    }

    @Override
    public Resource createResource(String resourceName, String libraryName, String contentType) {
        log.fine("calling createResource(" + resourceName + ", " + libraryName + "," + contentType + ")");
        Resource r = getWrapped().createResource(resourceName, libraryName, contentType);
        return r;
    }

    @Override
    public boolean libraryExists(String libraryName) {
        log.fine("calling libraryExists : " + libraryName);
        return getWrapped().libraryExists(libraryName);
    }

    /**
     * Not so nice workaround, because the ResourceResolver might
     * not be in the ServletContext when the class is created.
     * @return
     */
    private PrimitiveResourceResolver getResourceResolver() {
        if(resourceResolver == null)
            resourceResolver = (PrimitiveResourceResolver) ctx.getAttribute(Constants.CONTEXT_RESOURCE_RESOLVER_ATTRIBUTE_NAME);

        return resourceResolver;
    }
}
