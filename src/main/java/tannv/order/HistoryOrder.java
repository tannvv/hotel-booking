/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.order;

import java.sql.Date;
import java.util.ArrayList;
import tannv.orderDetail.OrderDetailDTO;

/**
 *
 * @author TanNV
 */
public class HistoryOrder {
    private String hotelName,createDate;
    private int totalCost,orderID;
    private ArrayList<OrderDetailDTO> detailOrder ;
    private Date checkIn, checkOut;

    public HistoryOrder() {
    }

    public HistoryOrder(String hotelName, String createDate, int totalCost, int orderID, ArrayList<OrderDetailDTO> detailOrder, Date checkIn, Date checkOut) {
        this.hotelName = hotelName;
        this.createDate = createDate;
        this.totalCost = totalCost;
        this.orderID = orderID;
        this.detailOrder = detailOrder;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public ArrayList<OrderDetailDTO> getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(ArrayList<OrderDetailDTO> detailOrder) {
        this.detailOrder = detailOrder;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

   
    

    @Override
    public String toString() {
        return "HistoryOrder{" + "hotelID=" + hotelName + ", createDate=" + createDate + ", totalCost=" + totalCost + ", orderID=" + orderID + ", detailOrder=" + detailOrder + ", checkIn=" + checkIn + ", checkOut=" + checkOut + '}' + "\n";
    }

    
    
}
