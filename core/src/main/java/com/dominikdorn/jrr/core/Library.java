package com.dominikdorn.jrr.core;

import org.apache.commons.digester.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester.annotations.rules.ObjectCreate;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Library {

    String id;
    List<LibraryEntry> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LibraryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LibraryEntry> entries) {
        this.entries = entries;
    }
}
