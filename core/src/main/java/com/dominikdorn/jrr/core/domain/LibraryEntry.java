package com.dominikdorn.jrr.core.domain;

/**
 * This is the base class for an Entry inside of a Library,
 * e.g. <em>jquery/jquery.js</em> in Library <em>primefaces</em>.
 *
 * Every <code>LibraryEntry</code> contains an <code>id</code> and
 * a <code>name</code>.
 */
public abstract class LibraryEntry {
    String id;
    String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
