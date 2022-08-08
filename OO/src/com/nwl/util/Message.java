package com.nwl.util;

import java.io.Serializable;
import java.util.List;
import com.nwl.entiy.User;

public class Message implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -2373243212467713871L;
    private int messageType;
            String chatcontention;
            User user=null;
            List<User> users=null;
            String [] contants=null;
            byte[] image=null;

    
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getChatcontention() {
        return chatcontention;
    }

    public void setChatcontention(String chatcontention) {
        this.chatcontention = chatcontention;
        this.contants=chatcontention.split(",");
    }

    public String[] getContants() {
        return contants;
    }

    public void setContants(String[] contants) {
        this.contants = contants;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
