package com.dominikdorn.jrr.core;

import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;
import org.apache.commons.digester.annotations.rules.ObjectCreate;
import org.apache.commons.digester.annotations.rules.SetNext;

import java.util.ArrayList;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Relocator {

    List<Mirror> mirrors = new ArrayList<Mirror>();
    List<Library> libraries = new ArrayList<Library>();

    public List<Mirror> getMirrors() {
        return mirrors;
    }

    public void setMirrors(List<Mirror> mirrors) {
        this.mirrors = mirrors;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void addLibrary(final Library library) throws ConfigurationParsingException {
        if(null == library.id || library.id.isEmpty())
            throw new ConfigurationParsingException("found library with missing id attribute");
        libraries.add(library);
    }

    public void addMirror(Mirror mirror)
    {
        this.mirrors.add(mirror);
    }

    public void setRoot(Relocator relocator)
    {
        System.out.println("replacing this relocator ( " + this +" ) with other relocator " + relocator);
        this.libraries = relocator.libraries;
        this.mirrors = relocator.mirrors;
    }
}
