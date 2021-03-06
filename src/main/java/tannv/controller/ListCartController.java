/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tannv.account.AccountDTO;
import tannv.orderDetail.OrderDetailDTO;
import tannv.room.RoomDAO;
import tannv.room.RoomDTO;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "ListCartController", urlPatterns = {"/ListCartController"})
public class ListCartController extends HttpServlet {

    private final String VIEW_CART = "viewCartPage";
    private final String LISTCART_ERROR = "listCartNullPage";
    private final String LOGGIN_ERROR = "loginPageError";
    private final String LOADLIST = "loadListAction";
    private final String DETAIL_HOTEL = "detailHotelAction";
    private final Logger LOGGER = Logger.getLogger(ListCartController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        AccountDTO userData = (AccountDTO) session.getAttribute("userData");
        if (userData == null) {
            request.setAttribute("error", "You cannot have permission to to this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGGIN_ERROR);
            rd.forward(request, response);
        }

        try {
            String action = request.getParameter("action");
            //String id = request.getParameter("id"); // watch id to do action
            if (action.equals("view") || action.equals("")) { // view
                ArrayList<OrderDetailDTO> listCart = null;
                listCart = (ArrayList<OrderDetailDTO>) request.getSession().getAttribute("listCart");
                if (listCart == null) {
                    listCart = new ArrayList<>();
                    request.getSession().setAttribute("listCart", listCart);
                    request.setAttribute("error", "List cart is null");
                    RequestDispatcher rd = request.getRequestDispatcher(LISTCART_ERROR);
                    rd.forward(request, response);
                }
                request.setAttribute("totalCost", getTotalCost(listCart, request, response));
                RequestDispatcher rd = request.getRequestDispatcher(VIEW_CART);
                rd.forward(request, response);
            }
            if (action.equals("buy")) { // add to cart
                buyCart(request, response);
            }
            if (action.equals("remove")) { // remove cart
                removeCart(request, response);
            }
            if (action.equals("update")) {
                updateCart(request, response);
            }
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void buyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        try {
            HttpSession session = request.getSession();
            Date checkIn = (Date) session.getAttribute("checkIn");
            if (checkIn == null) {
                request.setAttribute("error", "Select date to check in check out");
                RequestDispatcher rd = request.getRequestDispatcher(LOADLIST);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Select date to check in check out");
            RequestDispatcher rd = request.getRequestDispatcher(LOADLIST);
            rd.forward(request, response);
        }

        // Flow 
        // Step 1 : l???y gi??? h??ng t??? tr??n session xu???ng ????? x??? l??
        // Step 2 : l???y ID c???a s???n ph???m ng?????i d??ng mu???n th??m v??o gi??? h??ng
        // Step 3 : ki???m tra xem gi??? h??ng ???? t???n t???i s???n ph???m ng?????i d??ng mu???n mua ch??a
        // - n???u s???n ph???m ???? c?? trong gi??? h??ng => th??m m???i s???n ph???m
        // - n???u s???n ph???m ch??? c?? trongn gi??? h??ng => c???p nh???t s??? l?????ng c???a s???n ph???m += 1
        // Step 4 : c???p nh???t l???i gi??? h??ng tr??n session
        // l???y gi??? h??ng t??? session
        ArrayList<OrderDetailDTO> listCart = (ArrayList<OrderDetailDTO>) request.getSession().getAttribute("listCart");
        if (listCart == null) {
            listCart = new ArrayList<>();
        }
        // l???y ID c???a s???n ph???m m?? kh??ch h??ng mu???n th??m v??o gi??? h??ng
        int id = Integer.parseInt(request.getParameter("id"));
        // ki???m tra s???n ph???m ???? t???n t???i trong gi??? h??ng hay ch??a
        int indexCart = checkExistedCart(listCart, id);
        if (indexCart >= 0) { // tr?????ng h???p ???? t???n t???i t???i th??m t??ng s??? l?????ng l??n 1
            int newQuantity = listCart.get(indexCart).getQuantity() + 1;
            listCart.get(indexCart).setQuantity(newQuantity);
        } else {    // tr?????ng h???p ch??a t???n t???i th?? th??m m???i s???n ph???m
            RoomDAO roomDAO = new RoomDAO();
            RoomDTO room = roomDAO.getRoomByID(id);
            listCart.add(new OrderDetailDTO(room, 1));
        }
        // l???y t???ng gi?? tr??? c???a gi??? h??ng
        request.setAttribute("totalCost", getTotalCost(listCart, request, response));
        // c???p nh???t l???i gi??? h??ng tr??n session
        request.getSession().setAttribute("listCart", listCart);
        RequestDispatcher rd = request.getRequestDispatcher(DETAIL_HOTEL);
        rd.forward(request, response);

    }

    private int checkExistedCart(ArrayList<OrderDetailDTO> listCart, int roomID) {
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).getRoomID().getRoomID() == roomID) {
                return i;
            }
        }
        return -1;
    }

    private void removeCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<OrderDetailDTO> listCart = (ArrayList<OrderDetailDTO>) request.getSession().getAttribute("listCart");
        int index = checkExistedCart(listCart, id);
        listCart.remove(index);
        request.getSession().setAttribute("listCart", listCart);
        request.getRequestDispatcher(VIEW_CART).forward(request, response);
    }

    private int getTotalCost(ArrayList<OrderDetailDTO> listCart, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Date checkIn = (Date) session.getAttribute("checkIn");
        Date checkOut = (Date) session.getAttribute("checkOut");
        long numberDate = Util.getNumberDay(checkIn, checkOut);
        int result = 0;
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).isStatus() == true) {
                result += (listCart.get(i).getRoomID().getPrice() * listCart.get(i).getQuantity() * numberDate);
            }
        }
        return result;
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<OrderDetailDTO> listCart = (ArrayList<OrderDetailDTO>) request.getSession().getAttribute("listCart");
        String roomID = request.getParameter("roomID");

        // get value in listcart need to change
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String sts = request.getParameter("status");
        boolean status = Boolean.parseBoolean(sts);

        int index = checkExistedCart(listCart, Integer.parseInt(roomID));

        // set value
        listCart.get(index).setQuantity(quantity);
        listCart.get(index).setStatus(status);
        HttpSession session = request.getSession();
        session.setAttribute("totalCost", getTotalCost(listCart, request, response));
        request.setAttribute("listCart", listCart);
        request.getRequestDispatcher(VIEW_CART).forward(request, response);

    }
}
