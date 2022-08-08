package com.nwl.util;

import java.awt.Color;
import java.net.Socket;

import javax.swing.JLabel;

import com.nwl.view.FriendsLIst;

public class FriendsThread extends Thread{
    Socket socket;
    FriendsLIst friendsLIst;
    boolean isclose=false;
    public FriendsThread(Socket socket, FriendsLIst friendsLIst) {
        this.socket = socket;
        this.friendsLIst=friendsLIst;
    }

    @Override
    public void run()
    {
        try{
            while (!isclose) {
                Message message=new Message();
                message=SocketUtil.geSocketUtil().readObject(socket, message);
                if(message.getMessageType()==MessageType.REFRESH_FRIENDS)
                {
                    JLabel[] jLabels=friendsLIst.getjLabels();
                    for(int i=0;i<jLabels.length;i++)
                    {
                        if(jLabels[i].getText().equals(message.getChatcontention())){
                            if(jLabels[i].isEnabled())
                            {
                                jLabels[i].setForeground(Color.LIGHT_GRAY);
                                jLabels[i].setEnabled(false);
                                friendsLIst.revalidate();
                            }else{
                                jLabels[i].setForeground(Color.BLACK);
                                jLabels[i].setEnabled(true);
                                friendsLIst.revalidate();
                            }
                        }
                    }
                }
                else if(message.getMessageType()==MessageType.U_GOT_NEWMSG)
                {
                    JLabel[] jLabels=friendsLIst.getjLabels();
                    for(int i=0;i<jLabels.length;i++)
                    {
                        if(jLabels[i].getText().equals(message.getContants()[0])){
                            jLabels[i].setBackground(Color.GREEN);
                        }
                    }
                }
            }
            //socket.close();
        }catch(Exception e)
        {
            isclose=true;
           // e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
