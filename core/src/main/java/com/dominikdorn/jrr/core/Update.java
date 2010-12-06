package com.dominikdorn.jrr.core;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
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
