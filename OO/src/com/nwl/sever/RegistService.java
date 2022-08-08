package com.nwl.sever;

import java.net.Socket;

import com.nwl.entiy.User;
import com.nwl.util.Message;
import com.nwl.util.MessageType;
import com.nwl.util.PropertiesUtil;
import com.nwl.util.SocketUtil;

public class RegistService {

    public User user=null;
    Message message=new Message();

    public String regist(User user) {
        try {
            /* 发送给user至服务端进行核对 */
            Socket socket=new Socket(PropertiesUtil.gPropertiesUtil().getValue("hostip"), 9999);
            message.setMessageType(MessageType.REGIST);
            message.setUser(user);
            SocketUtil.geSocketUtil().writeObject(socket, message);
           /*  从服务端收到请求回应 */
            message=SocketUtil.geSocketUtil().readObject(socket, message);
            //Message message=(Message)objectInputStream.readObject();

            if(message.getMessageType()==MessageType.REGIST_SUCCESS)
            {
                return message.getUser().getId();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
