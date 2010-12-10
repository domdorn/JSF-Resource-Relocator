package com.dominikdorn.jrr.core.domain;

import com.dominikdorn.jrr.exceptions.ConfigurationException;
import com.dominikdorn.jrr.exceptions.ConfigurationParsingException;

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
    Update update = new Update();


    public List<Mirror> getMirrors() {
        return mirrors;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void addLibrary(final Library library) throws ConfigurationParsingException {
        if(null == library)
            throw new IllegalArgumentException("This may not happen: Tried to add a null-library");
        if(null == library || null == library.id || library.id.isEmpty())
            throw new ConfigurationParsingException("found library with missing id attribute");
        libraries.add(library);
    }

    public void addMirror(Mirror mirror) throws ConfigurationParsingException {
        if(null == mirror)
            throw new IllegalArgumentException("This may not happen: Tried to add a null-mirror");
        if(null == mirror || null == mirror.id || mirror.id.isEmpty())
            throw new ConfigurationParsingException("found a mirror with missing id attribute");

        this.mirrors.add(mirror);
    }

    public void setUpdate(Update update) {
        if(null == update)
            throw new IllegalArgumentException("This may not happen: Update set to null");
        update.checkIntegrity();
        this.update = update;
    }

    /**
     * This method checks the integrity of the configuration,
     * meaning there are no duplicate <em>id</em>s, <em>with</em>-references correctly point
     * to registered <em>id</em>s and so on.
     *
     * @throws ConfigurationException if an error in the configuration is found.
     */
    public void checkIntegrity() throws ConfigurationException
    {

    }
}
