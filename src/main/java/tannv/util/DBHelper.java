/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author TanNV
 */
public class DBHelper {
     public static Connection getConnection() 
            throws  SQLException, NamingException {
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("DBCon");
        Connection con = ds.getConnection();
        return con;
    }
     
    public static Connection getConnection_2(){
        Connection conn = null;
         try{
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             String url = "jdbc:sqlserver://localhost:1433;databaseName=Booking_Hotel";
             conn = DriverManager.getConnection(url, "sa", "123");
         }catch(ClassNotFoundException | SQLException ex){
             ex.printStackTrace();
         }
         return conn;
    }
}
