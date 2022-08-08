package com.nwl.dao;

import java.sql.ResultSet;
import java.sql.Connection;
import com.nwl.util.JdbcUtil;
import java.sql.PreparedStatement;
import com.nwl.entiy.Sayings;

public class SayingsDao {
    public void insertSayings(Sayings  sayings) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            /* sql查询部分，密码做加密处理 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            connection.setAutoCommit(false);
            StringBuffer buffer = new StringBuffer("insert into sayings(me,lea,contains,date,image) values(?,?,?,?,?)");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, sayings.getMe());
            preparedStatement.setString(2, sayings.getLea());
            preparedStatement.setString(3, sayings.getContains());
            preparedStatement.setString(4, sayings.getDate());
            preparedStatement.setBytes(5, sayings.getImage());
            preparedStatement.execute();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
        }
    }
 
    public boolean isOnline(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /* sql查询部分，密码做加密处理 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            connection.setAutoCommit(false);
            StringBuffer buffer = new StringBuffer("select isonline from user where name=?");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("isonline");
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
        }
        return false;
    }
}
