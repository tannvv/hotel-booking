/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "UpdateDateController", urlPatterns = {"/UpdateDateController"})
public class UpdateDateController extends HttpServlet {

    private final String INDEX_USER_PAGE = "indexUserPage";
    private final String INDEX_GUEST_PAGE = "indexGuestPage";
    private final String LOGIN_ERROR = "loginPageError";
    private final String VIEW_CART = "viewCartPage";
    private final Logger LOGGER = Logger.getLogger(UpdateDateController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        AccountDTO userData = (AccountDTO) session.getAttribute("userData");
        if (userData == null) {
            request.setAttribute("error", "You cannot have permission to to this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGIN_ERROR);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");

            validDate(request, response);

            if (userData == null) {
                request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
            } else {
                if (userData.getRole() == 1) {      // member
                    request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void validDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            Date checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkIn"));
            Date checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkOut"));
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
            String checkInString = frm.format(checkIn);
            String checkOutString = frm.format(checkOut);
            request.setAttribute("checkInData", checkInString);
            request.setAttribute("checkOutData", checkOutString);

            if (Util.getNumberDay(checkIn, checkOut) >= 30 || Util.getNumberDay(checkIn, checkOut) <= 0) {
                request.setAttribute("error", "Check in cannot more than check out and it limited about 30 day");
                RequestDispatcher rd = request.getRequestDispatcher(VIEW_CART);
                request.setAttribute("error", "Check in check out limited about 30 day, and cannot choose a date in the past");
                rd.forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("checkIn", checkIn);
                session.setAttribute("checkOut", checkOut);
                session.setAttribute("numberDate", Util.getNumberDay(checkIn, checkOut));

                ArrayList<OrderDetailDTO> listCart = (ArrayList<OrderDetailDTO>) session.getAttribute("listCart");
                session.setAttribute("totalCost", getTotalCost(listCart, request, response));
                RequestDispatcher rd = request.getRequestDispatcher(VIEW_CART);
                rd.forward(request, response);
            }
        } catch (IOException | ParseException | ServletException e) {
            LOGGER.error(e.toString());
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_CART);
            request.setAttribute("error", "Check in check out limited about 30 day, and cannot choose a date in the past");
            rd.forward(request, response);
        }

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

}
