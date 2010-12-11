package com.dominikdorn.jrr.core;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ResolverResult {

    public static enum TYPE { UNDEFINED_FIRST, ABSOLUTE, EXCLUDED, NOT_MANAGED, UNDEFINED_LAST };

    private TYPE type;

    public static ResolverResult foundAbsolute(String url)
    {
        return new ResolverResult(ResolverResult.TYPE.ABSOLUTE, url);
    }

    public static ResolverResult excluded()
    {
        return new ResolverResult(ResolverResult.TYPE.EXCLUDED, null);
    }

    public static ResolverResult notManaged()
    {
        return new ResolverResult(ResolverResult.TYPE.NOT_MANAGED, null);
    }

    private ResolverResult(TYPE type, String url) {
        this.type = type;
        this.url = url;
    }

    String url;

    public String getUrl() {
        return url;
    }

    public TYPE getType() {
        return type;
    }
}
