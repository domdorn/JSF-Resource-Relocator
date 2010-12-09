package com.dominikdorn.jrr.core;

/**
 * This <em>LibraryEntry</em> says we want to <em>Exclude</em>
 * this <em>Resource</em> from the <em>Library</em>.
 *
 * What this means is, that a request for a excluded <em>Resource</em>
 * should result in a <em>null</em> Value returned by the <em>resolver</em>
 * which then leads to ignoring the <em>Resource</em> while rendering.
 *
 */
public class ExcludeLibraryEntry extends LibraryEntry {
/* name already given in abstract LibraryEntry */
}
