/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tannv.util.DBHelper;

/**
 *
 * @author TanNV
 */
public class OrderDAO {

    private Connection conn;
    private PreparedStatement ptsm;
    private ResultSet rs;

    final static Logger LOGGER = Logger.getLogger(OrderDAO.class);
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

    public boolean createOrder(OrderDTO order) throws SQLException {
        boolean result = false;
        try {
            String sql = "INSERT INTO Orders(hotelID,userID,totalCost,createDate,checkinDate,checkOutDate,discount) "
                    + "VALUES (?,?,?,?,?,?,?)";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, order.getHotelID());
            ptsm.setString(2, order.getUserID());
            ptsm.setInt(3, order.getTotalCost());
            ptsm.setString(4, order.getCreateDate());
            ptsm.setDate(5, order.getCheckInDate());
            ptsm.setDate(6, order.getCheckOutDate());
            ptsm.setInt(7, order.getDiscount());
            int check = ptsm.executeUpdate();
            if (check == 1) {
                result = true;
            }
        } catch (SQLException | NamingException e) {
             LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }

    public OrderDTO getOrderByID(int orderID) throws SQLException {
        OrderDTO order = null;
        try {
            String sql = "SELECT hotelID,userID,totalCost,createDate,checkinDate,checkOutDate,discount FROM Orders \n"
                    + "WHERE OrderID = ?";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, orderID);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                int hotelID = rs.getInt("hotelID");
                String userID = rs.getString("userID");
                int totalCost = rs.getInt("totalCost");
                String createDate = rs.getString("createDate");
                Date checkInDate = rs.getDate("checkinDate");
                Date checkOutDate = rs.getDate("checkoutDate");
                int discount = rs.getInt("discount");
                order = new OrderDTO(orderID, hotelID, totalCost, discount, userID, checkInDate, checkOutDate,createDate);
            }
        } catch (SQLException | NamingException e) {
             LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }
        return order;
    }

    public int getOrderID(String userID, String createDate) throws SQLException {
        int orderID = -1;
        try {
            String sql = "SELECT orderID FROM Orders \n"
                    + "WHERE userID = ? AND createDate = ?";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, userID);
            ptsm.setString(2, createDate);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                orderID = rs.getInt("orderID");
            }
        } catch (SQLException | NamingException e) {
             LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }
        return orderID;
    }
}
