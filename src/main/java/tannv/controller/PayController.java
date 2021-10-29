/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import tannv.order.ListOrder;
import tannv.order.OrderDAO;
import tannv.order.OrderDTO;
import tannv.orderDetail.OrderDetailDAO;
import tannv.orderDetail.OrderDetailDTO;
import tannv.room.RoomDAO;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "PayController", urlPatterns = {"/PayController"})
public class PayController extends HttpServlet {

    private final String OTP_ERROR_PAGE = "otpEmailPageError";
    private final String INVALID_PAGE = "invalidPage";
    private final String VIEW_CART_PAGE = "viewCartPage";
    private final String LOAD_LIST = "loadListAction";
    private final String LOGGIN_ERROR = "loginPageError";
    private final Logger LOGGER = Logger.getLogger(PayController.class);

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
        try {
            HttpSession session = request.getSession();

            int otp = 0;
            int otpSession = (int) session.getAttribute("otp");

            try {
                otp = Integer.parseInt(request.getParameter("otp"));
            } catch (NumberFormatException e) {
                LOGGER.error(e.getMessage());
                request.setAttribute("error", "OTP is incrrect");
                RequestDispatcher rd = request.getRequestDispatcher(OTP_ERROR_PAGE);
                rd.forward(request, response);
            }

            if (otp == otpSession) {  // chap nhan thanh toan
                OrderDetailDAO detailDAO = new OrderDetailDAO();
                OrderDAO orderDAO = new OrderDAO();
                AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");

                //get list cart from session
                ArrayList<OrderDetailDTO> listCartSession = (ArrayList<OrderDetailDTO>) request.getSession().getAttribute("listCart");
                // get rooms have status is true in the listcart
                ArrayList<OrderDetailDTO> listCart = getListCartValid(listCartSession);
                //check quantity to store the database
                Date checkIn = (Date) session.getAttribute("checkIn");
                Date checkOut = (Date) session.getAttribute("checkOut");
                SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
                String checkInString = frm.format(checkIn);
                String checkOutString = frm.format(checkOut);
                boolean checkAmount = RoomDAO.validListCartToBuy(listCart, checkInString, checkOutString);
                if (checkAmount) {

                    long numberDate = Util.getNumberDay(checkIn, checkOut);

                    String notifi = "";

                    //return orders split by hotelID
                    ArrayList<ListOrder> listOrder = splitOrder(request, response);
                    for (int i = 0; i < listOrder.size(); i++) {
                        //create order
                        String today = LocalDateTime.now().toString();
                        int hotelID = listOrder.get(i).getListOrder().get(0).getHotelID().getHotelID();
                        int totalCost = getTotalCost(listOrder.get(i).getListOrder(), numberDate);
                        OrderDTO order = new OrderDTO(hotelID, totalCost, userData.getEmail(), Util.utilDateToSqlDate(checkIn),
                                Util.utilDateToSqlDate(checkOut), today);
                        orderDAO.createOrder(order);
                        int orderID = orderDAO.getOrderID(userData.getEmail(), today);
                        ArrayList<OrderDetailDTO> listCartThisOrder = listOrder.get(i).getListOrder();
                        setOrderIdToListCart(listCartThisOrder, orderID);
                        detailDAO.storeListCart(listCartThisOrder);
                        notifi += "Create new order ID : " + orderID + "\n";
                    }

                    request.setAttribute("notifi", notifi);
                    request.getSession().setAttribute("listCart", getListCartInValid(listCartSession));
                    RequestDispatcher rd = request.getRequestDispatcher(LOAD_LIST);
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Out of stock, cannot buy");
                    RequestDispatcher rd = request.getRequestDispatcher(VIEW_CART_PAGE);
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("error", "OTP is incrrect");
                RequestDispatcher rd = request.getRequestDispatcher(OTP_ERROR_PAGE);
                rd.forward(request, response);
            }
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.toString());
            response.sendRedirect(INVALID_PAGE);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void setOrderIdToListCart(ArrayList<OrderDetailDTO> listCart, int orderID) {
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            listCart.get(i).setOrderID(new OrderDTO(orderID));
        }
    }

    private int getTotalCost(ArrayList<OrderDetailDTO> listCart, long numberDate) {
        int result = 0;
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).isStatus() == true) {
                result += listCart.get(i).getRoomID().getPrice() * listCart.get(i).getQuantity() * numberDate;
            }
        }
        return result;
    }

    private ArrayList<OrderDetailDTO> getListCartValid(ArrayList<OrderDetailDTO> listCart) {
        ArrayList<OrderDetailDTO> result = new ArrayList<>();
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).isStatus() == true) {
                result.add(listCart.get(i));
            }
        }
        return result;
    }

    private ArrayList<OrderDetailDTO> getListCartInValid(ArrayList<OrderDetailDTO> listCart) {
        ArrayList<OrderDetailDTO> result = new ArrayList<>();
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).isStatus() == false) {
                result.add(listCart.get(i));
            }
        }
        return result;
    }

    private ArrayList<ListOrder> splitOrder(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ArrayList<OrderDetailDTO> listCartSession = (ArrayList<OrderDetailDTO>) session.getAttribute("listCart");

        ArrayList<OrderDetailDTO> listCart = getListCartValid(listCartSession);
        ArrayList<ListOrder> listOrderSplit = new ArrayList<>();
        //get ids hotel existed in the list cart
        ArrayList<Integer> arrayHotelID = getIDHotels(listCart);
        // split to orders form list cart
        for (int i = 0; i < arrayHotelID.size(); i++) {
            //create new list cart
            ArrayList<OrderDetailDTO> listDetail = new ArrayList<>();
            for (int j = 0; j < listCart.size(); j++) {
                if (arrayHotelID.get(i) == listCart.get(j).getHotelID().getHotelID()) {
                    // add cart have same id with id in arrayHotelID
                    listDetail.add(listCart.get(j));
                }
            }
            ListOrder listOrder = new ListOrder();
            listOrder.setListOrder(listDetail);
            listOrderSplit.add(listOrder);
        }

        return listOrderSplit;
    }

    private ArrayList<Integer> getIDHotels(ArrayList<OrderDetailDTO> listCart) {
        ArrayList<Integer> listHotelIDs = new ArrayList<>();
        int size = listCart.size();
        int[] numberHotel = new int[size];
        for (int i = 0; i < size; i++) {
            numberHotel[i] = listCart.get(i).getHotelID().getHotelID();
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numberHotel[i] == numberHotel[j]) {
                    numberHotel[j] = -1;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (numberHotel[i] != -1) {
                listHotelIDs.add(numberHotel[i]);
            }
        }

        return listHotelIDs;
    }

}
