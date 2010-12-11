package com.dominikdorn.jrr.core.domain;

import org.apache.commons.digester.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester.annotations.rules.ObjectCreate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class specifies a <em>Mirror</em>.
 *
 * A <em>Mirror</em> is usually a specific host providing certain <em>Library</em>s
 * under a specific <em>basePath</em>.
 */
public class Mirror {

    String id;
    String basePath;
    List<String> libraries = new ArrayList<String>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLibraries() {
        return libraries;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void addLibrary(String libraryId)
    {
        libraries.add(libraryId);
    }

    public void addLibrary(MirrorFor mirrorFor)
    {
        libraries.add(mirrorFor.getLibraryId());
    }
}
