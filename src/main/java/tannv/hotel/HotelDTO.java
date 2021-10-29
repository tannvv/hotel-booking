/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.hotel;

import java.io.Serializable;

/**
 *
 * @author TanNV
 */
public class HotelDTO implements Serializable{
    private int hotelID;
    private String hotelName, address,image;

    public HotelDTO() {
    }

    public HotelDTO(int hotelID, String hotelName, String address, String image) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.address = address;
        this.image = image;
    }

    

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "HotelDTO{" + "hotelID=" + hotelID + ", hotelName=" + hotelName + ", address=" + address + ", image=" + image + '}';
    }
    
    
}
