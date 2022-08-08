package com.nwl.dao;

import java.sql.Connection;
import com.nwl.util.JdbcUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.nwl.entiy.User;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User login(String id, String pwd) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            /* sql查询部分，密码做加密处理 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            connection.setAutoCommit(false);
            StringBuffer buffer = new StringBuffer("select id,name,tele,pwd,photo from user where id=? and pwd=?");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pwd);
            // preparedStatement.setString(3,
            // PropertiesUtil.gPropertiesUtil().getValue("pwdkey"));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("name"));
                user.setTele(resultSet.getString("tele"));
                user.setPhoto(resultSet.getInt("photo"));
            }
            //PreparedStatement pparedStatement=null;
            if(user!=null)
            {
            StringBuffer buffer2 = new StringBuffer("update user set isonline = 1 where id = ?");
            preparedStatement = connection.prepareStatement(buffer2.toString());
            preparedStatement.setString(1, user.getId());
            preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
        }
        return user;
    }

    public User regist(String name, String pwd, String tele,int photo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        Random r = new Random();
        try {
            /* sql查询部分，密码做加密处理 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            StringBuffer buffer = new StringBuffer("select id,name,tele,pwd from user where name=?");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return null;
            } else {
                String number = "1234567890";
                StringBuffer stringBuffer = new StringBuffer();
                do {
                    buffer = new StringBuffer("select id,name,tele,pwd from user where id=?");
                    preparedStatement = connection.prepareStatement(buffer.toString());
                    for (int i = 0; i < 10; i++) {
                        if (i == 0) {
                            stringBuffer.append(number.charAt(r.nextInt(8)));
                        }
                        stringBuffer.append(number.charAt(r.nextInt(9)));
                    }
                    preparedStatement.setString(1, stringBuffer.toString());
                    resultSet = preparedStatement.executeQuery();
                } while (resultSet.next());
                user = new User();
                user.setId(stringBuffer.toString());
                user.setUsername(name);
                user.setPassword(pwd);
                user.setTele(tele);
                user.setPhoto(photo);
                buffer = new StringBuffer("insert into user values(?,?,?,?,?,0)");
                preparedStatement = connection.prepareStatement(buffer.toString());
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getTele());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, user.getPhoto());
                preparedStatement.execute();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
        }
        return null;
    }

    public List<User> getFriends(String name) {
        Connection connection = null;
        PreparedStatement pparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> friends = null;
        try {
            /* sql查询部分 */
            connection = JdbcUtil.getJdbcUtil().getConnection();
            StringBuffer buffer = new StringBuffer("select id,name,photo,isonline from user");
            pparedStatement = connection.prepareStatement(buffer.toString());
            resultSet = pparedStatement.executeQuery();
            if (resultSet!=null) {
                friends = new ArrayList<User>();

                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setUsername(resultSet.getString("name"));
                    user.setPhoto(resultSet.getInt("photo"));
                    user.setIsonline(resultSet.getInt("isonline")==1);
                    if(!user.getUsername().equals(name))
                        { friends.add(user);}
                }
            }
            return friends;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(resultSet, pparedStatement, connection);
        }
        return null;
    }

    public User logout(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = JdbcUtil.getJdbcUtil().getConnection();
            connection.setAutoCommit(false);
            StringBuffer buffer = new StringBuffer("select id,name from user where name=?");
            preparedStatement = connection.prepareStatement(buffer.toString());
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("name"));
            }
            StringBuffer buffer2 = new StringBuffer("update user set isonline = 0 where name = ?");
            preparedStatement = connection.prepareStatement(buffer2.toString());
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            connection.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 养成用完关闭连接的好习惯 */
            JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
        }
        return null;
    }
}
