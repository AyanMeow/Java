package com.nwl.sever;

import java.net.Socket;
import javax.imageio.ImageIO;
import com.nwl.util.Message;
import com.nwl.util.MessageType;
import com.nwl.util.SocketUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ChatSevice {
    private BufferedImage image=null;

    public String chatto(String content,Socket socket){
        try {
            Message message=new Message();
            message.setMessageType(MessageType.TALK);
            message.setChatcontention(content);  
            if(this.image!=null)
            {
                ByteArrayOutputStream out=new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", out);
                byte[] byteImage=out.toByteArray();
                message.setImage(byteImage);
            }
            SocketUtil.geSocketUtil().writeObject(socket, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
