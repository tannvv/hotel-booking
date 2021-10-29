/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.order;

import java.sql.Date;

/**
 *
 * @author TanNV
 */
public class OrderDTO {
    private int orderID, hotelID,totalCost,discount;
    private String userID;
    private Date checkInDate, checkOutDate;
    private String createDate;

    public OrderDTO() {
    }

    public OrderDTO(int orderID) {
        this.orderID = orderID;
    }

    public OrderDTO(int hotelID, int totalCost, String userID, Date checkInDate, Date checkOutDate, String createDate) {
        this.hotelID = hotelID;
        this.totalCost = totalCost;
        this.userID = userID;
 
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.createDate = createDate;
    }

    public OrderDTO(int orderID, int hotelID, int totalCost, int discount, String userID, Date checkInDate, Date checkOutDate, String createDate) {
        this.orderID = orderID;
        this.hotelID = hotelID;
        this.totalCost = totalCost;
        this.discount = discount;
        this.userID = userID;
    
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.createDate = createDate;
    }
    
    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


  

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    
}
