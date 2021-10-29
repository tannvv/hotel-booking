/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.account;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tannv.util.DBHelper;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
public class AccountDAO {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private final static Logger LOGGER = Logger.getLogger(AccountDAO.class);
    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    public boolean checkLogin(String username, String password) throws NamingException , SQLException {
        boolean check = false;
        try {
            String sql = "SELECT status FROM Account WHERE email = ? and password = ?";
            con = DBHelper.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, Util.toSha256(password));
            rs = pst.executeQuery();
            while (rs.next()) {
                check = true;
            }
        }catch(SQLException | NamingException ex){
            LOGGER.error("Check login is error" + ex.getMessage());
        }
        finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean checkEmail(String email) throws NamingException , SQLException {
        try {
            String sql = "SELECT status FROM Account WHERE email = ?";
            con = DBHelper.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                return true;
            }
        }catch(SQLException | NamingException ex){
            LOGGER.error("Check email existed is error" + ex.getMessage());
        } 
        finally {
            closeConnection();
        }
        return false;
    }
    
    public AccountDTO createAccount(AccountDTO account) throws NamingException , SQLException {
        try {
            String sql = "INSERT INTO Account(email,password,status,role,createDate) \n"
                    + "VALUES (?,?,?,?,?)";
            con = DBHelper.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, account.getEmail());
            pst.setString(2, Util.toSha256(account.getPassword()));
            pst.setInt(3, account.getStatus());
            pst.setInt(4, account.getRole());
            pst.setDate(5, account.getCreateDate());
            if (pst.executeUpdate() == 1) {
                return account;
            }
        }catch(SQLException | NamingException ex){
            
            LOGGER.error("Create accout is error" + ex.getMessage());
        } 
        finally {
            closeConnection();
        }
        return null;
    }
    
    public AccountDTO getAccountByEmail(String email) throws NamingException , SQLException {
        AccountDTO account = null;
        try {
            String sql = "SELECT password, role,status,createDate FROM Account\n"
                    + "WHERE email = ?";
            con = DBHelper.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                int role = rs.getInt("role");
                int status = rs.getInt("status");
                Date createDate = rs.getDate("createDate");
                account = new AccountDTO(email, password, status, role, createDate);
            }
        }catch(SQLException | NamingException ex){
            LOGGER.error("Create accout is error" + ex.getMessage());
        } 
        finally {
            closeConnection();
        }
        return account;
    }
}
