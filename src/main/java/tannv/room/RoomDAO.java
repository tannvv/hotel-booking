/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tannv.orderDetail.OrderDetailDTO;
import tannv.util.DBHelper;

/**
 *
 * @author TanNV
 */
public class RoomDAO {

    private Connection conn;
    private PreparedStatement ptsm;
    private ResultSet rs;
    final static Logger LOGGER = Logger.getLogger(RoomDAO.class);

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

    public RoomDTO getRoomByID(int roomID) throws SQLException {
        RoomDTO room = null;
        String sql = "SELECT hotelID, type, amount,price FROM Room \n"
                + "WHERE roomID = ? ";
        try {
            conn = DBHelper.getConnection_2();

            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, roomID);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                int hotel = rs.getInt("hotelID");
                int type = rs.getInt("type");
                int amout = rs.getInt("amount");
                int price = rs.getInt("price");
                room = new RoomDTO(roomID, hotel, type, amout, price);
            }
        } catch (SQLException ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return room;
    }

    public ArrayList<RoomDTO> getRoomByHotelID(int hotelID) throws SQLException {
        ArrayList<RoomDTO> litsRoom = null;
        String sql = "SELECT roomID, type, amount,price FROM Room \n"
                + "WHERE hotelID = ? ";
        try {
            conn = DBHelper.getConnection();

            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, hotelID);
            rs = ptsm.executeQuery();
            litsRoom = new ArrayList<>();
            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                int type = rs.getInt("type");
                int amout = rs.getInt("amount");
                int price = rs.getInt("price");
                RoomDTO room = new RoomDTO(roomID, hotelID, type, amout, price);
                litsRoom.add(room);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return litsRoom;
    }

    public boolean validQuantity(int roomID, int quantity) throws SQLException {
        boolean result = false;
        try {

            String sql = "SELECT amount FROM Room \n"
                    + "WHERE roomID= ?";
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);

            ptsm.setInt(1, roomID);

            rs = ptsm.executeQuery();
            rs.next();
            int amount = rs.getInt("amount"); // amount of cake existed in system
            if ((amount - quantity) >= 0) {
                result = true;
            }
        } catch (SQLException | NamingException e) {
            LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }

        return result;
    }

    public int numberOfRoomRentDateToDate(int roomID, String checkIn, String checkOut) throws SQLException {
        int numberRoom = 0;
        try {

            String sql = "select SUM(quantity) as 'sumQuantity' from orders ord inner join OrderDetail od on ord.orderID = od.orderID\n"
                    + "	where ((? between ord.checkinDate and ord.checkOutDate) OR (? between ord.checkinDate and ord.checkOutDate))\n"
                    + "		AND roomID = ?";
            conn = DBHelper.getConnection_2();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, checkIn);
            ptsm.setString(2, checkOut);
            ptsm.setInt(3, roomID);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                numberRoom = rs.getInt("sumQuantity");
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }
        return numberRoom;
    }
    
    public boolean validRoomToBuy(int roomID, int quantity, String checkId, String checkOut) throws SQLException {
        boolean result = false;
        try {   
            RoomDAO roomDAO = new RoomDAO();
            RoomDTO room = roomDAO.getRoomByID(roomID);
            int quantityRented = roomDAO.numberOfRoomRentDateToDate(roomID, checkId, checkOut);
            if (((room.getAmount() - quantityRented)- quantity) >= 0 ) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.toString());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public static boolean validListCartToBuy(ArrayList<OrderDetailDTO> listCart,String checkIn,String checkOut ) throws SQLException{
        RoomDAO dao = new RoomDAO();
        for (int i = 0; i < listCart.size(); i++) {
           RoomDTO roomDb = dao.getRoomByID(listCart.get(i).getRoomID().getRoomID());
           int quantity = listCart.get(i).getQuantity();
           boolean checkUnit = dao.validRoomToBuy(roomDb.getRoomID(), quantity, checkIn, checkOut);
            if (checkUnit == false) {
                return false;
            }
        }
        return true;
    }

}
