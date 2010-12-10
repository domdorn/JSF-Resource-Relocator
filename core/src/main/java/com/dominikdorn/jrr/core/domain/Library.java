package com.dominikdorn.jrr.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a &quot;Library&quot; element
 * in the configuration file.
 *
 * A library contains an identifier <code>id</code> an a list of
 * <code>LibraryEntry</code>s.
 *
 * These entries are added to the library by the parser using
 * the <code>addEntry</code> method.
 */
public class Library {

    String id;
    List<LibraryEntry> entries = new ArrayList<LibraryEntry>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LibraryEntry> getEntries() {
        return entries;
    }

    public void addEntry(LibraryEntry entry)
    {
        if(entry == null)
            throw new IllegalArgumentException("This may not happen: Try to add a null LibraryEntry");
        entry.parent = this;
        entries.add(entry);
    }
}
