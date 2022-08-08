package com.nwl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/* jdbc工厂方法封装 */

public class JdbcUtil {
    private static JdbcUtil jdbcUtil=null;

    private JdbcUtil(){}

    public static JdbcUtil getJdbcUtil() {
        if(jdbcUtil==null)
            jdbcUtil= new JdbcUtil();
        return jdbcUtil;
    }

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {    
            e.printStackTrace();
        }
    }

    /* 获取数据库连接 */

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(PropertiesUtil.gPropertiesUtil().getValue("url"),PropertiesUtil.gPropertiesUtil().getValue("name"),PropertiesUtil.gPropertiesUtil().getValue("pwd"));
    }

   /*  关闭数据量连接 */

   public void closeConnection(ResultSet resultSet,Statement statement,Connection connection) {
       try {
           if(resultSet!=null)
                resultSet.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }finally{
           try {
               if(statement!=null)
                    statement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }finally{
               try {
                   connection.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
       
   }
}
