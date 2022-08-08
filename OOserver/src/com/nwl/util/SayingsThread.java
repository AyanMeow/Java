package com.nwl.util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SayingsThread extends Thread {
    String targetname=null;
    String me=null;
    boolean isfound=false;
    boolean start=false;
    ChatThread targetT=null;

    
    @Override
    public void run()
    {
            while(!isfound){
                    targetT=ThreadManage.threadDictionary.get(targetname+"TO"+me);
                    if(targetT!=null)
                    {
                        isfound=true;
                    }
            }
            
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /* sql查询部分，密码做加密处理 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            connection.setAutoCommit(false);
            StringBuffer buffer = new StringBuffer("select * from sayings where me=? and lea=?");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, me);
            preparedStatement.setString(2, targetname);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Message message=new Message();
                message.setMessageType(MessageType.TALK);
                message.setChatcontention(resultSet.getString("me")+","+resultSet.getString("lea")+","+resultSet.getString("contains")+","+resultSet.getString("date"));
                message.setImage(resultSet.getBytes("image"));
                SocketUtil.geSocketUtil().writeObject(targetT.getSocket(), message);
            }
            PreparedStatement pparedStatement = null;
            StringBuffer buffer2 = new StringBuffer("delete from sayings where me=? and lea=?");
            pparedStatement = connection.prepareStatement(buffer2.toString());
            pparedStatement.setString(1, me);
            pparedStatement.setString(2, targetname);
            pparedStatement.execute();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
        }
        
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getTargetname() {
        return targetname;
    }

    public void setTargetname(String targetname) {
        this.targetname = targetname;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }
}
