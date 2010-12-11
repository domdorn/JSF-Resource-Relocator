package com.dominikdorn.jrr.jsf;

import com.dominikdorn.jrr.core.ResolverResult;
import com.dominikdorn.jrr.core.domain.*;

/**
 * A primitive ResourceResolver without using hashmaps etc.<br/>
 * <strong>Just a prove of concept.</strong>
 */
public class PrimitiveResourceResolver {

    Relocator relocator;

    public PrimitiveResourceResolver(Relocator relocator) {
        this.relocator = relocator;
    }


    public ResolverResult getResourceURL(String libraryId, String resourceName) {
        if (libraryId == null || resourceName == null || libraryId.isEmpty() || resourceName.isEmpty())
            throw new NullPointerException();

//        Library l = getLibrary(relocator, libraryId);
//        if (l == null)
//            return null;

        LibraryEntry le = getLibraryEntry(relocator, libraryId, resourceName);

//        for (LibraryEntry le : l.getEntries()) {
//            if (le.getName().equals(resourceName)) {
//                // found LE
//
//            }
//        }
        Library l;
        if(le == null)
        {
            l = getLibrary(relocator, libraryId);
            if(l == null)
                return ResolverResult.notManaged();
        }
        else
        {

            if(le instanceof ResourceLibraryEntry)
                le = resolveFinalLibraryEntry(relocator, (ResourceLibraryEntry) le);
            if(le instanceof ExcludeLibraryEntry)
                le = resolveFinalLibraryEntry(relocator, (ExcludeLibraryEntry) le);
            if(le instanceof ReplaceLibraryEntry){
                le = resolveFinalLibraryEntry(relocator, (ReplaceLibraryEntry) le);
                if(le == null)
                    return null;
                if(le instanceof ResourceLibraryEntry)
                    if( ((ResourceLibraryEntry) le).getAbsoluteUrl() != null)
                        return ResolverResult.foundAbsolute(((ResourceLibraryEntry) le).getAbsoluteUrl());

            }

            if(le == null)
                return ResolverResult.excluded();

            l = le.getParent();

            resourceName = le.getName();
        }

        Mirror m = getMirrorForLibrary(relocator, l.getId());
        if (m == null)
            return ResolverResult.notManaged();

        return ResolverResult.foundAbsolute(m.getBasePath() + "/" +l.getId() +"/" + resourceName);



    }

    private LibraryEntry getLibraryEntry(Relocator relocator, String libraryId, String resourceName) {
        Library l = getLibrary(relocator, libraryId);
        if (l != null) {
            for (LibraryEntry le : l.getEntries()) {
                if (le.getName().equals(resourceName)) {
                    // found LE
                    return le;
                }
            }
        }
        return null;
    }

    private Library getLibrary(Relocator relocator, String libraryId) {
        for (Library l : relocator.getLibraries()) {
            if (l.getId().equals(libraryId)) {
                return l;
            }
        }
        return null;

    }

    private Mirror getMirrorForLibrary(Relocator relocator, String libraryId) {
        for (Mirror m : relocator.getMirrors()) {
            for (String s : m.getLibraries()) {
                if (s.equals(libraryId)) {
                    // this mirror is a mirror of this library
                    return m;
                }
            }
        }
        return null;
    }

    private LibraryEntry resolveFinalLibraryEntry(Relocator relocator, ResourceLibraryEntry le)
    {
        return le;
    }

    private LibraryEntry resolveFinalLibraryEntry(Relocator relocator, ReplaceLibraryEntry le)
    {
        LibraryEntry replacement = getLibraryEntryWithId(relocator, le.getWith());
        if(replacement == null)
            return null;
        return replacement;
    }

    private LibraryEntry resolveFinalLibraryEntry(Relocator relocator, ExcludeLibraryEntry le)
    {
        return null;
    }

    private LibraryEntry resolveFinalLibraryEntry(Relocator relocator, LibraryEntry le)
    {
        throw new IllegalStateException("may never happen");
    }


    private LibraryEntry getLibraryEntryWithId(Relocator relocator, String id)
    {
        if(id == null || id.isEmpty())
            return null;

        for(Library l : relocator.getLibraries())
        {
            for(LibraryEntry le : l.getEntries())
            {
                if(le.getId() != null && le.getId().equals(id))
                    return le;
            }
        }
        return null;
    }
}
