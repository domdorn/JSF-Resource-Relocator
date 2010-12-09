package com.dominikdorn.jrr.core;

/**
 * This class holds information needed to update the
 * <em>ResourceRelocator</em> during runtime.
 *
 * It contains an additional <em>checkIntegrity()</em> method
 * that sets <em>enabled</em> to <em>false</em> if the default credentials
 * are used or no credentials are given.
 */
public class Update {

    public final static String DEFAULT_USERNAME = "admin";
    public final static String DEFAULT_PASSWORD = "default";


    boolean enabled = false;

    String user = DEFAULT_USERNAME;
    String pass = DEFAULT_PASSWORD;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    void checkIntegrity()
    {
        if(user == null || pass == null)
        {
            enabled = false;
            return;
        }

        if(user.equals(DEFAULT_USERNAME) && pass.equals(DEFAULT_PASSWORD))
        {
            enabled = false;
            return;
        }
        
    }
}
