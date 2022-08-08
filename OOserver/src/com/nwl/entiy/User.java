package com.nwl.entiy;

import java.io.Serializable;

public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -186678848098350637L;
    private String id;
    private String username;
    private String tele;
    private String password;
    private int photo;
    private boolean isonline;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTele() {
        return tele;
    }
    public void setTele(String tele) {
        this.tele = tele;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPhoto() {
        return photo;
    }
    public void setPhoto(int photo) {
        this.photo = photo;
    }
    public boolean isIsonline() {
        return isonline;
    }
    public void setIsonline(boolean isonline) {
        this.isonline = isonline;
    }
    
}
