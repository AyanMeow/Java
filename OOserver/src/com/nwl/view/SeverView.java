package com.nwl.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.nwl.dao.UserDao;
import com.nwl.entiy.User;
import com.nwl.util.ChatThread;
import com.nwl.util.Message;
import com.nwl.util.MessageType;
import com.nwl.util.SocketUtil;
import com.nwl.util.ThreadManage;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SeverView extends JFrame {

    UserDao userDao = new UserDao();
    OutputStream outputStream = null;
    ObjectOutputStream objectOutputStream = null;
    InputStream inputStream = null;
    ObjectInputStream objectInputStream = null;

    public void createFrame() {
        JLabel jLabel = new JLabel("Now is listening", JLabel.CENTER);
        this.add(jLabel);

        int width = 0;
        int height = 0;
        width = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("服务器端");
        this.setBounds((width - 400) / 2, (height - 350) / 2, 350, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void severListen() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            Message message = new Message();
            User user = new User();
            message = SocketUtil.geSocketUtil().readObject(socket, message);
            switch (message.getMessageType()) {
            case MessageType.LOGIN: {
                user = userDao.login(message.getUser().getId(), message.getUser().getPassword());
                if (user != null) {
                    message.setMessageType(MessageType.LGOIN_SUCCESS);
                    Message refresh=new Message();
                    refresh.setMessageType(MessageType.REFRESH_FRIENDS);
                    refresh.setChatcontention(user.getUsername()+"("+message.getUser().getId()+")");

                    Enumeration<String> keys=ThreadManage.socketDictionary.keys();
                   while(keys.asIterator().hasNext())
                   {
                       Socket refreshSocket=ThreadManage.socketDictionary.get(keys.asIterator().next());
                       SocketUtil.geSocketUtil().writeObject(refreshSocket, refresh);
                    }
                } else {
                    message.setMessageType(MessageType.LOGIN_FAILURE);
                }
                message.setUser(user);
                SocketUtil.geSocketUtil().writeObject(socket, message);
                break;
            }
            case MessageType.REGIST: {
                user = userDao.regist(message.getUser().getUsername(), message.getUser().getPassword(),
                        message.getUser().getTele(),message.getUser().getPhoto());
                if (user != null) {
                    message.setUser(user);
                    message.setMessageType(MessageType.REGIST_SUCCESS);
                    SocketUtil.geSocketUtil().writeObject(socket, message);
                } else {
                    message.setUser(user);
                    message.setMessageType(MessageType.REGIST_FAILURE);
                    SocketUtil.geSocketUtil().writeObject(socket, message);
                }
                break;
            }
            case MessageType.GET_FRIENDS:{
                message.setUsers(userDao.getFriends(message.getUser().getUsername()));
                if(message.getUsers()!=null)
                {
                    message.setMessageType(MessageType.GET_SUCCESS);
                }else{message.setMessageType(MessageType.GET_FAILURE);}
                SocketUtil.geSocketUtil().writeObject(socket, message);

                ThreadManage.socketDictionary.put(message.getUser().getUsername(), socket);
                break;
            }
            case MessageType.START_TALK: {
                ChatThread chatThread=new ChatThread(socket);
                chatThread.start();
                /* 以发送方名字建立对应词典 */
                ThreadManage.threadDictionary.put(message.getChatcontention(),chatThread);
                break;
            }
            case MessageType.LOG_OUT:{
                User use=userDao.logout(message.getUser().getUsername());
                Message refresh2=new Message();
                refresh2.setMessageType(MessageType.REFRESH_FRIENDS);
                refresh2.setChatcontention(use.getUsername()+"("+use.getId()+")");
                ThreadManage.socketDictionary.get(message.getUser().getUsername()).close();
                ThreadManage.socketDictionary.remove(message.getUser().getUsername());
                Enumeration<String> keys=ThreadManage.socketDictionary.keys();
                while(keys.asIterator().hasNext())
                {
                   Socket refreshSocket=ThreadManage.socketDictionary.get(keys.asIterator().next());
                   SocketUtil.geSocketUtil().writeObject(refreshSocket, refresh2);
                }
                break;
            }
            default:
                break;
            }
        }
    }

    public static void main(String[] args) {
        SeverView severView = new SeverView();
        severView.createFrame();
        try {
            severView.severListen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
