/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tannv.account.AccountDTO;
import tannv.hotel.HotelDAO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "LoadListController", urlPatterns = {"/LoadListController"})
public class LoadListController extends HttpServlet {

    
    private final String INDEX_USER_PAGE = "indexUserPage";
    private final String INDEX_GUEST_PAGE = "indexGuestPage";
    private final Logger LOGGER = Logger.getLogger(LoadListController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8"); 
        AccountDTO userData = (AccountDTO)request.getSession().getAttribute("userData");
        HotelDAO hotelDAO = new HotelDAO();
        int page = 0;
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ex) {
                page = 1;
                LOGGER.error("Page default = 1" + ex.getMessage());
            }
        int numberPage = hotelDAO.getPageNumberHotel();
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("listHotel", hotelDAO.getAllHotel(page));
        if (userData == null) {
            RequestDispatcher rd = request.getRequestDispatcher(INDEX_GUEST_PAGE);
            rd.forward(request, response);
        }else{
            if (userData.getRole() == 1) {      // member
                RequestDispatcher rd = request.getRequestDispatcher(INDEX_USER_PAGE);
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
                   processRequest(request, response);
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
