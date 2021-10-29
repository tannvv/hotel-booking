/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.order;

import java.util.ArrayList;
import tannv.orderDetail.OrderDetailDTO;

/**
 *
 * @author TanNV
 */
public class ListOrder {
    private ArrayList<OrderDetailDTO> listOrder = new ArrayList<>();

    public ListOrder() {
    }

    public ListOrder(ArrayList<OrderDetailDTO> listOrder) {
        this.listOrder = listOrder;
    }

    public ArrayList<OrderDetailDTO> getListOrder() {
        return listOrder;
    }

    public void setListOrder(ArrayList<OrderDetailDTO> listOrder) {
        this.listOrder = listOrder;
    }
    
}
