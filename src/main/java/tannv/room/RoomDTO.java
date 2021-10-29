/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.room;

import java.io.Serializable;

/**
 *
 * @author TanNV
 */
public class RoomDTO implements Serializable{
    private int roomID, hotelID, type, amount, price;

    public RoomDTO() {
    }

    public RoomDTO(int roomID) {
        this.roomID = roomID;
    }

    public RoomDTO(int roomID, int hotelID, int type, int amount, int price) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
