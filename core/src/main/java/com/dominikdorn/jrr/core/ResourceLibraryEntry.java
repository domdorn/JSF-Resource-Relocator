package com.dominikdorn.jrr.core;

/**
 * This <em>LibraryEntry</em> allows to specify an absolute
 * <em>URL</em> of a certain <em>Resource</em>
 */
public class ResourceLibraryEntry extends LibraryEntry {

    String absoluteUrl;

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }
}
