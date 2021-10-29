/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.orderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import tannv.order.OrderDTO;
import tannv.room.RoomDAO;
import tannv.room.RoomDTO;
import tannv.util.DBHelper;

/**
 *
 * @author TanNV
 */
public class OrderDetailDAO {
    private Connection conn = null;
    private PreparedStatement ptsm = null;
    private ResultSet rs = null;
     final static Logger LOGGER = Logger.getLogger(OrderDetailDAO.class);

    private void cleanConnect() throws SQLException {
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

    public boolean createOrderDetail(OrderDetailDTO orderDetail) throws SQLException {
        boolean result = false;
        try {
            String sql = "INSERT INTO OrderDetail(orderID, roomID, quantity) VALUES (?,?,?)";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, orderDetail.getOrderID().getOrderID());
            ptsm.setInt(2, orderDetail.getRoomID().getRoomID());
            ptsm.setInt(3, orderDetail.getQuantity());
            if (ptsm.executeUpdate() == 1) {
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }finally{
            cleanConnect();
        }

        return result;
    }
    
     public ArrayList<OrderDetailDTO> getOrderDetailByOrderID(int orderID) throws SQLException {
         ArrayList<OrderDetailDTO> list = null;
         RoomDAO roomDAO = new RoomDAO();
        try {
            String sql = "SELECT roomID, quantity FROM OrderDetail \n"
                    + "WHERE orderID = ?";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, orderID);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) { 
                RoomDTO room = roomDAO.getRoomByID(rs.getInt("roomID"));
                OrderDTO order = new OrderDTO(orderID);
                OrderDetailDTO orderDetail = new OrderDetailDTO(order,room,rs.getInt("quantity"));
                list.add(orderDetail);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }finally{
            cleanConnect();
        }

        return list;
    }
    
    public boolean validListCartToPay(ArrayList<OrderDetailDTO> listCart){
        RoomDAO dao = new RoomDAO();
        try {
            int size = listCart.size();
            for (int i = 0; i < size; i++) {
                // check quantity for all cake
                boolean check = dao.validQuantity(listCart.get(i).getRoomID().getRoomID(),listCart.get(i).getQuantity() );
                if (check == false) {
                    return false;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return true;
    }
    
    public boolean updateAmountRoom(OrderDetailDTO orderDetail){
        boolean result = false;
        RoomDAO romAO = new RoomDAO();
        try {
            RoomDTO room = romAO.getRoomByID(orderDetail.getRoomID().getRoomID());
            int newAmount = room.getAmount() - orderDetail.getQuantity();
            String sql = "UPDATE Room \n"
                    + "SET amount = ? \n"
                    + "WHERE roomID = ?";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, newAmount);
            ptsm.setInt(2, orderDetail.getRoomID().getRoomID());
            if (ptsm.executeUpdate() == 1) {
                result = true;
            }        
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return result;
    }
    
    public boolean storeListCart(ArrayList<OrderDetailDTO> listCart){
        boolean result = false;
        
        try {
            // store list cart to database
            int size = listCart.size();
            for (int i = 0; i < size; i++) {
                createOrderDetail(listCart.get(i));
            }
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        
        return result;
    }
    
}
