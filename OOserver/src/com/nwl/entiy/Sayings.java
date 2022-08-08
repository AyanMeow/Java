package com.nwl.entiy;

import java.io.Serializable;

public class Sayings implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5886937832987844443L;
    private String me;
    private String lea;
    private String contains;
    private String date;
    private byte[] image=null;
    
    public String getMe() {
        return me;
    }
    public void setMe(String me) {
        this.me = me;
    }
    public String getLea() {
        return lea;
    }
    public void setLea(String lea) {
        this.lea = lea;
    }
    public String getContains() {
        return contains;
    }
    public void setContains(String contains) {
        this.contains = contains;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
