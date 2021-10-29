/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tannv.account.AccountDTO;
import tannv.order.HistoryOrder;
import tannv.order.HistoryOrderDAO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "SearchOrderName", urlPatterns = {"/SearchOrderName"})
public class SearchOrderName extends HttpServlet {

    private final String LOGGIN_ERROR = "loginPageError";
    private final String HISTORY_PAGE = "historyOrderPage";
    private final String INVALID_PAGE = "invalidPage";
      private final String LOGIN_PAGE = "loginPageError";
    private final Logger LOGGER = Logger.getLogger(RequestEmailController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        
         HttpSession session = request.getSession();
        AccountDTO userData = (AccountDTO) session.getAttribute("userData");
        if (userData == null) {
            request.setAttribute("error", "You cannot have permission to to this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGGIN_ERROR);
            rd.forward(request, response);
        }
        String hotelName = request.getParameter("hotelName");
        request.setAttribute("hotelName", hotelName);
        if (hotelName == null) {
            request.setAttribute("error", "You need permission to do this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
            rd.forward(request, response);
        }
        try {
            if (userData.getRole() == 1) {  //user
            HistoryOrderDAO dao = new HistoryOrderDAO();
            String name = request.getParameter("hotelName");
            ArrayList<HistoryOrder> listHistoryOrder = dao.getHistoryOrderByHotelName(name, userData.getEmail());
                if (listHistoryOrder == null || listHistoryOrder.isEmpty()) {
                    request.setAttribute("error", "Cannot find any name hotel to tracking order");
                }
            request.setAttribute("listHistoryOrder", listHistoryOrder);
            RequestDispatcher rd = request.getRequestDispatcher(HISTORY_PAGE);
            rd.forward(request, response);
        }
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.getMessage());
            response.sendRedirect(INVALID_PAGE);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
