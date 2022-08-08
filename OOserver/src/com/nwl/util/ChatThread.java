package com.nwl.util;

import java.net.Socket;
import com.nwl.dao.SayingsDao;
import com.nwl.entiy.Sayings;

public class ChatThread extends Thread {
    Socket socket;
    boolean close=false;
    SayingsDao sayingsDao=new SayingsDao();
    public ChatThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run(){
        while(!close){
            try {
                Message message=new Message();
                message=SocketUtil.geSocketUtil().readObject(socket, message);
                if(message.getMessageType()==MessageType.TALK)
                {
                    System.out.println(message.getChatcontention());
                    Socket leasSocket=null;
                    ChatThread temp;
                    temp=ThreadManage.threadDictionary.get(message.getContants()[1]+"TO"+message.getContants()[0]);
                  
                   if(temp!=null){
                        Message responMessage=message;
                        leasSocket=temp.getSocket();
                        responMessage.setMessageType(MessageType.TALK);
                        SocketUtil.geSocketUtil().writeObject(leasSocket, responMessage);
                    }else{
                        Sayings sayings=new Sayings();
                        sayings.setMe(message.contants[0]);
                        sayings.setLea(message.contants[1]);
                        sayings.setContains(message.contants[2]);
                        sayings.setDate(message.contants[3]);
                        sayings.setImage(message.image);
                        sayingsDao.insertSayings(sayings);

                        //查询对方是否在线
                        String obname="";
                        int i=0;
                        while(sayings.getLea().charAt(i)!='(')
                        {
                            i++;
                        }
                        obname=sayings.getLea().substring(0, i);

                        Message leavingMessage=new Message();
                        leavingMessage.setMessageType(MessageType.U_GOT_NEWMSG);
                        leavingMessage.setChatcontention(sayings.getMe()+","+sayings.getLea()+","+sayings.getContains()+","+sayings.getDate());
                        leavingMessage.setImage(sayings.getImage());
                        
                        if(sayingsDao.isOnline(obname))
                        {
                            SocketUtil.geSocketUtil().writeObject(ThreadManage.socketDictionary.get(obname),leavingMessage);
                            SayingsThread sayingsThread=new SayingsThread();
                            sayingsThread.setMe(sayings.getMe());
                            sayingsThread.setTargetname(sayings.getLea());
                            sayingsThread.run();
                        }else{
                            OfflineSayingThread offlineSayingThread=new OfflineSayingThread(sayings.getMe(), sayings.getLea(), leavingMessage);
                            offlineSayingThread.run();
                        }
                    }
                }
                if(message.getMessageType()==MessageType.TALK_CLOSE)
                {
                    close=true;
                    ThreadManage.threadDictionary.remove(message.getChatcontention());
                }
            } catch (Exception e) {
                //e.printStackTrace();
                close=true;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
