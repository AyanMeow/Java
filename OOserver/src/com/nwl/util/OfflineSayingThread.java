package com.nwl.util;

import com.nwl.dao.SayingsDao;

public class OfflineSayingThread extends Thread{
    String me=null;
    String lea=null;
    Message message=null;
    public OfflineSayingThread(String me, String lea,Message message) {
        this.me = me;
        this.lea = lea;
        this.message=message;
    }
     @Override
     public void run(){
        String obname="";
        int i=0;
        while(lea.charAt(i)!='(')
        {
            i++;
        }
        obname=lea.substring(0, i);

        SayingsDao  sayingsDao=new SayingsDao();
        while(!sayingsDao.isOnline(obname))
        {
            int donothing;
        }        
        try{
            sleep(1000);
            SocketUtil.geSocketUtil().writeObject(ThreadManage.socketDictionary.get(obname),message);
            SayingsThread sayingsThread=new SayingsThread();
            sayingsThread.setMe(me);
            sayingsThread.setTargetname(lea);
            sayingsThread.run();
        }catch(Exception e)
        {
        e.printStackTrace();
        }
    }
}
