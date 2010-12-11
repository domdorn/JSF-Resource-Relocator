package com.dominikdorn.jrr.core.domain;

/**
 * Just a temporary object to hold the
 * <pre>
  * <mirrors>
 *      <mirror ...>
 *          <for ...>
 *      </mirror>
 *  </mirrors>
 * </pre>
 * structure.
 */
public class MirrorFor {
    String libraryId;

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }
}
