package com.nwl.sever;

import java.io.File;
import java.net.Socket;
import java.util.List;
import com.nwl.entiy.User;
import com.nwl.util.FriendsThread;
import com.nwl.util.Message;
import com.nwl.util.MessageType;
import com.nwl.util.PropertiesUtil;
import com.nwl.util.SocketUtil;
import com.nwl.view.*;
public class LoginSevice {
    FriendsThread friendsThread;
    public User me;
    public User login(User user) {
        Socket socket=null;
        try {
            /* 发送给user至服务端进行核对 */
            socket=new Socket(PropertiesUtil.gPropertiesUtil().getValue("hostip"), 9999);
            Message message=new Message();
            message.setMessageType(MessageType.LOGIN);
            message.setUser(user);
            SocketUtil.geSocketUtil().writeObject(socket, message);

           /*  从服务端收到请求回应 */
            message=SocketUtil.geSocketUtil().readObject(socket, message);

            if(message.getMessageType()==MessageType.LGOIN_SUCCESS)
                {
                    this.me=message.getUser();
                    File u=new File("C:\\OO\\"+message.getUser().getUsername()+"("+message.getUser().getId()+")");
                    u.mkdir();
                    return message.getUser();
                }
            else if(message.getMessageType()==MessageType.LOGIN_FAILURE)
               { return null;}

            //socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> friends(User user, FriendsLIst friendsLIst){
        try {
            Message message=new Message();
            Socket socket=new Socket(PropertiesUtil.gPropertiesUtil().getValue("hostip"), 9999);
            message.setMessageType(MessageType.GET_FRIENDS);
            message.setUser(user);
            SocketUtil.geSocketUtil().writeObject(socket, message);
            message=SocketUtil.geSocketUtil().readObject(socket, message);
            friendsThread=new FriendsThread(socket, friendsLIst);
            friendsThread.start();

            if(message.getMessageType()==MessageType.GET_SUCCESS)
            {
                return message.getUsers();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logout(String name)
    {
        Message logoutMessage=new Message();
        logoutMessage.setMessageType(MessageType.LOG_OUT);
        User user=new User();
        user.setUsername(name);
        logoutMessage.setUser(user);
        //Socket logoutSocket=friendsThread.getSocket();
        try {
            Socket socket=new Socket(PropertiesUtil.gPropertiesUtil().getValue("hostip"), 9999);
            SocketUtil.geSocketUtil().writeObject(socket, logoutMessage);
            //logoutSocket.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
