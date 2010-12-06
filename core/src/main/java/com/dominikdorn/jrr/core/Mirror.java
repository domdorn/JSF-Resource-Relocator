package com.dominikdorn.jrr.core;

import org.apache.commons.digester.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester.annotations.rules.ObjectCreate;

import java.util.ArrayList;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
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

    public void setLibraries(List<String> libraries) {
        this.libraries = libraries;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
