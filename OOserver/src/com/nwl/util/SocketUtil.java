package com.nwl.util;

import java.net.Socket;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SocketUtil {
    private static SocketUtil socketUtil=null;

    private SocketUtil(){};

    public static SocketUtil geSocketUtil() {
        if(socketUtil==null)
        {
            socketUtil=new SocketUtil();
            return socketUtil;
        }
        return socketUtil;
    }

    public void writeObject(Socket socket,Message message)throws Exception{
        OutputStream outputStream=socket.getOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
    }

    public Message readObject(Socket socket,Message message)throws Exception{
        InputStream inputStream=socket.getInputStream();
        ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
        message=(Message)objectInputStream.readObject();
        /* message.setMessageType(((Message)objectInputStream.readObject()).getMessageType());
        message.setUser(((Message)objectInputStream.readObject()).getUser()); */
        return message;
    }
}
