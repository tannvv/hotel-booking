package tannv.orderDetail;

import java.io.Serializable;
import java.sql.SQLException;
import tannv.hotel.HotelDAO;
import tannv.hotel.HotelDTO;
import tannv.order.OrderDTO;
import tannv.room.RoomDTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TanNV
 */
public class OrderDetailDTO implements  Serializable{
    private OrderDTO orderID;
    private RoomDTO roomID;
    private HotelDTO hotelID;
    private int quantity;
    private boolean status;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(RoomDTO roomID, int quantity) throws SQLException {
        this.roomID = roomID;
        this.quantity = quantity;
        HotelDAO dao = new HotelDAO();
        this.hotelID = dao.getHotelID(roomID.getHotelID());
        this.status = true;
    }

    public OrderDetailDTO(OrderDTO orderID, RoomDTO roomID, int quantity) throws SQLException {
        this.orderID = orderID;
        this.roomID = roomID;
        this.quantity = quantity;
        HotelDAO dao = new HotelDAO();
        this.hotelID = dao.getHotelID(roomID.getHotelID());
    }
    
    public OrderDTO getOrderID() {
        return orderID;
    }

    public void setOrderID(OrderDTO orderID) {
        this.orderID = orderID;
    }

    public RoomDTO getRoomID() {
        return roomID;
    }

    public void setRoomID(RoomDTO roomID) {
        this.roomID = roomID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public HotelDTO getHotelID() {
        return hotelID;
    }

    public void setHotelID(HotelDTO hotelID) {
        this.hotelID = hotelID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" + "orderID=" + orderID + ", roomID=" + roomID + ", hotelID=" + hotelID + ", quantity=" + quantity + ", status=" + status + '}';
    }
    
    
    
    
}
