package com.dominikdorn.jrr.core.domain;

/**
 * This <em>LibraryEntry</em> is supposed to replace an existing
 * <em>LibraryEntry</em> (not necessarily specified in the configuration file)
 * with an reference to another <em>LibraryEntry</em>.
 *
 * This effectively allows to replace a certain script (e.g. <em>jquery.js</em>)
 * in one <em>Library</em> (e.g. <em>primefaces</em>) with a <em>resource</em>
 * within another <em>Library</em> (pointing to an <em>id</em> of the other <em>LibraryEntry</em>).
 */
public class ReplaceLibraryEntry extends LibraryEntry {

    String with;

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }
}
