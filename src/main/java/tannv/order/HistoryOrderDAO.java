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
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tannv.orderDetail.OrderDetailDAO;
import tannv.orderDetail.OrderDetailDTO;
import tannv.util.DBHelper;

/**
 *
 * @author TanNV
 */
public class HistoryOrderDAO {

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

    public ArrayList<HistoryOrder> getAllHistoryOrder(String userID) throws SQLException {
        ArrayList<HistoryOrder> list = null;
        try {
            String sql = "SELECT hotelName, orderID,createDate,checkinDate,checkOutDate,totalCost \n"
                    + "FROM Hotel h INNER JOIN Orders o ON h.hotelID=o.hotelID \n"
                    + "WHERE o.userID = ? \n"
                    + "ORDER BY createDate DESC";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, userID);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            while (rs.next()) {
                String hotelName = rs.getString("hotelName");
                int orderID = rs.getInt("orderID");
                String createDate = rs.getString("createDate").substring(0, 16);
                Date checkIn = rs.getDate("checkinDate");
                Date checkOut = rs.getDate("checkOutDate");
                int totalCost = rs.getInt("totalCost");
                ArrayList<OrderDetailDTO> orderDetails = orderDetailDAO.getOrderDetailByOrderID(orderID);
                HistoryOrder history = new HistoryOrder(hotelName, createDate, totalCost, orderID, orderDetails, checkIn, checkOut);
                list.add(history);
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }

        return list;
    }
    
    public ArrayList<HistoryOrder> getHistoryOrderByHotelName(String name, String userID) throws SQLException {
        ArrayList<HistoryOrder> list = null;
        try {
            String sql = "SELECT hotelName, orderID,createDate,checkinDate,checkOutDate,totalCost \n"
                    + "FROM Hotel h INNER JOIN Orders o on h.hotelID=o.hotelID \n"
                    + "WHERE hotelName LIKE ? AND o.userID = ? \n"
                    + "ORDER BY createDate DESC";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, "%" + name + "%");
            ptsm.setString(2, userID);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            while (rs.next()) {
                String hotelName = rs.getString("hotelName");
                int orderID = rs.getInt("orderID");
                String createDate = rs.getString("createDate").substring(0,16);
                Date checkIn = rs.getDate("checkinDate");
                Date checkOut = rs.getDate("checkOutDate");
                int totalCost = rs.getInt("totalCost");
                ArrayList<OrderDetailDTO> orderDetails = orderDetailDAO.getOrderDetailByOrderID(orderID);
                HistoryOrder history = new HistoryOrder(hotelName, createDate, totalCost, orderID, orderDetails, checkIn, checkOut);
                list.add(history);
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }

        return list;
    }
}
