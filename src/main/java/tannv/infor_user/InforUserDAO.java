/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.infor_user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import tannv.util.DBHelper;

/**
 *
 * @author TanNV
 */
public class InforUserDAO {

    private Connection conn;
    private PreparedStatement ptsm;
    private ResultSet rs;
    final static Logger LOGGER = Logger.getLogger(InforUserDAO.class);

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ptsm != null) {
            ptsm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public InforUserDTO getInforByID(String email) throws SQLException {
        InforUserDTO infor = null;
        String sql = "SELECT name, phone, address FROM InforUser \n"
                + "WHERE email = ?";
        try {
            conn = DBHelper.getConnection();

            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, email);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
              
                infor = new InforUserDTO(email, name, phone, address);
            }
        } catch (Exception ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return infor;
    }

    public InforUserDTO createInfor(InforUserDTO infor) throws SQLException {
        String sql = "INSERT INTO InforUser(email,name,phone,address) VALUES (?,?,?,?)";
        try {
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, infor.getEmail());
            ptsm.setString(2, infor.getName());
            ptsm.setString(3, infor.getPhone());
            ptsm.setString(4, infor.getAddress());
            if (ptsm.executeUpdate() == 1) {
                return infor;
            }
        } catch (Exception ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}
