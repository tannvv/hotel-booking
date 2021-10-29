/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
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
@WebServlet(name = "HistoryOrderController", urlPatterns = {"/HistoryOrderController"})
public class HistoryOrderController extends HttpServlet {

    private final String LOGGIN_ERROR = "loginPageError";
    private final String HISTORY_PAGE = "historyOrderPage";
     private final String INVALID_PAGE = "invalidPage";
    private final Logger LOGGER = Logger.getLogger(HistoryOrderController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        AccountDTO userData = (AccountDTO) session.getAttribute("userData");
        if (userData == null) {
            request.setAttribute("error", "You cannot have permission to to this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGGIN_ERROR);
            rd.forward(request, response);
        }
        try {
             if (userData.getRole() == 1) {  //user
            HistoryOrderDAO dao = new HistoryOrderDAO();
            ArrayList<HistoryOrder> listHistoryOrder = dao.getAllHistoryOrder(userData.getEmail());
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(HistoryOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(HistoryOrderController.class.getName()).log(Level.SEVERE, null, ex);
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

}
