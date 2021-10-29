/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tannv.util.DBHelper;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
public class HotelDAO {

    private Connection conn;
    private PreparedStatement ptsm;
    private ResultSet rs;
    final static Logger LOGGER = Logger.getLogger(HotelDAO.class);

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

    public ArrayList<HotelDTO> getAllHotel(int page) throws SQLException {
        ArrayList<HotelDTO> listHotel = null;
        String sql = "SELECT hotelID,hotelName,address,image FROM Hotel \n"
                + "ORDER BY hotelName \n"
                + "OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
        try {
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, ((page - 1) * 20));
            rs = ptsm.executeQuery();
            listHotel = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("hotelID");
                String name = rs.getString("hotelName");
                String address = rs.getString("address");
                String image = rs.getString("image");
                HotelDTO hotel = new HotelDTO(id, name, address, image);
                listHotel.add(hotel);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return listHotel;
    }

    public ArrayList<HotelDTO> getHotelNameAndAmount(String name, int amount) throws SQLException {
        ArrayList<HotelDTO> listHotel = null;
        String sql = "select hotelID,hotelName,address from Hotel where hotelName LIKE ? AND hotelID in (\n"
                + "						select hotelID from Room \n"
                + "						group by hotelID\n"
                + "						having sum(amount) >= ?\n"
                + "								)  ";
        try {
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            rs = ptsm.executeQuery();
            listHotel = new ArrayList<>();
            if (rs.next()) {
                int id = rs.getInt("hotelID");
                String hotelName = rs.getString("hotelName");
                String image = rs.getString("image");
                String address = rs.getString("address");
                HotelDTO hotel = new HotelDTO(id, hotelName, address, image);
                listHotel.add(hotel);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error("Get infor user by id is error" + ex.getMessage());
        } finally {
            closeConnection();
        }
        return listHotel;
    }

    public ArrayList<HotelDTO> getHotelName(String name, int page) throws SQLException {
        ArrayList<HotelDTO> listHotel = null;
        String sql = "SELECT hotelID,hotelName,address,image FROM Hotel where hotelName LIKE ?  \n"
                + "ORDER BY hotelName \n"
                + "OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
        try {
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, "%" + name + "%");
            ptsm.setInt(2, ((page - 1) * 20));
            rs = ptsm.executeQuery();
            listHotel = new ArrayList<>();
            if (rs.next()) {
                int id = rs.getInt("hotelID");
                String hotelName = rs.getString("hotelName");
                String image = rs.getString("image");
                String address = rs.getString("address");
                HotelDTO hotel = new HotelDTO(id, hotelName, address, image);
                listHotel.add(hotel);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeConnection();
        }
        return listHotel;
    }

    public ArrayList<HotelDTO> getHotelByNumberRoomAndCheckInCheckOutDate(int numberRoom, String checkIn, String checkOut, int page) throws SQLException {
        ArrayList<HotelDTO> listHotel = null;
        String sql = "";

        try {
            if (numberOrderDateToDate(checkIn, checkOut) > 0) { // existed order date to date
            sql = "SELECT hotelID,hotelName,address,image FROM Hotel WHERE hotelID IN (SELECT RENT.hotelID FROM (SELECT hotelID, SUM(sumQuanity) as 'rented' FROM (SELECT roomID ,SUM(quantity) AS 'sumQuanity',ord.hotelID as hotelID\n"
                    + "FROM orders ord INNER JOIN OrderDetail od on ord.orderID = od.orderID\n"
                    + "WHERE (? BETWEEN ord.checkinDate AND ord.checkOutDate) OR (? BETWEEN ord.checkinDate AND ord.checkOutDate)\n"
                    + "GROUP BY roomID,ord.hotelID\n"
                    + ") AS Caculate\n"
                    + "GROUP BY hotelID) AS RENT INNER JOIN (select hotelID, SUM(amount) as 'exist' from Room\n"
                    + "group by hotelID) AS EXIST\n"
                    + "ON RENT.hotelID = EXIST.hotelID AND (EXIST.exist - RENT.rented) >= ?)\n"
                    + "ORDER BY hotelName\n"
                    + "OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
            conn = DBHelper.getConnection_2();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, checkIn);
            ptsm.setString(2, checkOut);
            ptsm.setInt(3, numberRoom);
            ptsm.setInt(4, ((page - 1) * 20));
            rs = ptsm.executeQuery();
            listHotel = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("hotelID");
                String hotelName = rs.getString("hotelName");
                String image = rs.getString("image");
                String address = rs.getString("address");
                HotelDTO hotel = new HotelDTO(id, hotelName, address, image);
                listHotel.add(hotel);
            }
        } else {
            sql = "SELECT hotelID,hotelName,address,image FROM Hotel\n"
                    + "	WHERE hotelID IN (SELECT hotelID FROM Room \n"
                    + "				GROUP BY hotelID\n"
                    + "				HAVING SUM(amount) >= ?)\n"
                    + "	ORDER BY hotelName\n"
                    + "	OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
             conn = DBHelper.getConnection_2();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, numberRoom);
            ptsm.setInt(2, ((page - 1) * 20));
            rs = ptsm.executeQuery();
            listHotel = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("hotelID");
                String hotelName = rs.getString("hotelName");
                String image = rs.getString("image");
                String address = rs.getString("address");
                HotelDTO hotel = new HotelDTO(id, hotelName, address, image);
                listHotel.add(hotel);
            }
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOGGER.error(ex.getMessage());
        } finally {
            closeConnection();
        }
        return listHotel;
    }

    public HotelDTO getHotelID(int id) throws SQLException {
        HotelDTO hotel = null;
        String sql = "SELECT hotelName,address,image FROM Hotel where hotelID = ?";
        try {
            conn = DBHelper.getConnection();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, id);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                String hotelName = rs.getString("hotelName");
                String image = rs.getString("image");
                String address = rs.getString("address");
                hotel = new HotelDTO(id, hotelName, address, image);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeConnection();
        }
        return hotel;
    }

    public int getPageNumberHotel() throws SQLException {
        int result = -1;
        try {
            String sql = "SELECT count(hotelID) AS numberPage\n"
                    + "FROM Hotel";

            conn = DBHelper.getConnection_2();
            ptsm = conn.prepareStatement(sql);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("numberPage");
                result = Util.numberPage(count, 20);
            }
        } catch (SQLException e) {
            LOGGER.error("Get number page of hotel is error" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int numberOrderDateToDate(String checkIn, String checkOut) throws SQLException {
        int numberOrder = -1;
        try {
            String sql = " SELECT count(*) as numberOrder\n"
                    + "		FROM orders\n"
                    + "		WHERE (? BETWEEN checkinDate AND checkOutDate) \n"
                    + "		OR (? BETWEEN checkinDate AND checkOutDate)";

            conn = DBHelper.getConnection_2();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, checkIn);
            ptsm.setString(2, checkOut);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                numberOrder = rs.getInt("numberOrder");
            }
        } catch (SQLException e) {
            LOGGER.error("Get number page of hotel is error" + e.getMessage());
        } finally {
            closeConnection();
        }
        return numberOrder;
    }
}
