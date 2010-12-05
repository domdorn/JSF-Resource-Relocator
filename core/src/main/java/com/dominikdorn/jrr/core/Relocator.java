package com.dominikdorn.jrr.core;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Relocator {

    List<Mirror> mirrors;

    List<Library> libraries;

    public List<Mirror> getMirrors() {
        return mirrors;
    }

    public void setMirrors(List<Mirror> mirrors) {
        this.mirrors = mirrors;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }
}
