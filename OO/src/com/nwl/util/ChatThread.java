package com.nwl.util;

import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;
import com.nwl.view.ImageView;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Date;

public class ChatThread extends Thread{
    Socket socket;
    JTextArea jTextArea;
    boolean close=false;
    Date nowdate=new Date();
    public ChatThread(Socket socket,JTextArea jTextArea)
    {
        this.socket=socket;
        this.jTextArea=jTextArea;
    }

    @Override
    public void run(){
        try{
            while (!close) {
                Message responMessage=new Message();
                responMessage=SocketUtil.geSocketUtil().readObject(socket, responMessage);
                if(responMessage.getMessageType()==MessageType.TALK)
                {
                    jTextArea.append(responMessage.getContants()[0]+" | "+responMessage.getContants()[3]+"\n");
                    jTextArea.append(">>>>"+responMessage.getContants()[2]+"\n");
                    if(responMessage.image!=null)
                    {
                        /* jTextArea.append(responMessage.getContants()[0]+" | "+responMessage.getContants()[3]+"\n");
                        jTextArea.append(">>>>我发送了一个图片\n"); */
                        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(responMessage.image);
                        BufferedImage bufferedImage=ImageIO.read(byteArrayInputStream);
                        ImageView imageView=new ImageView(bufferedImage);
                        imageView.createFrame();
                    }
                }
            }
            //socket.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
