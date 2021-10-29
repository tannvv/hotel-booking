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
import org.apache.log4j.Logger;
import tannv.account.AccountDTO;
import tannv.hotel.HotelDAO;
import tannv.hotel.HotelDTO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "SearchHotelName", urlPatterns = {"/SearchHotelName"})
public class SearchHotelName extends HttpServlet {

    private final String INDEX_USER_PAGE = "indexUserPage";
    private final String INDEX_GUEST_PAGE = "indexGuestPage";
    private final String LOGIN_PAGE = "loginPageError";
    private final Logger LOGGER = Logger.getLogger(SearchHotelName.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String hotelName = request.getParameter("hotelName");
        if (hotelName == null) {
            request.setAttribute("error", "You need permission to do this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
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
        try {
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
            HotelDAO hotelDAO = new HotelDAO();
            String hotelName = request.getParameter("hotelName");
            request.setAttribute(hotelName, "hotelName");
            validString(request, response);
            request.setAttribute("hotelName", hotelName);
            int page = 0;
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ex) {
                page = 1;
                LOGGER.error("Page default = 1" + ex.getMessage());
            }
            int numberPage = hotelDAO.getPageNumberHotel();
            request.setAttribute("numberPage", numberPage);

            ArrayList<HotelDTO> listHotel = hotelDAO.getHotelName(hotelName, page);
            validListHotel(listHotel, request, response);
            request.setAttribute("listHotel", listHotel);
            if (userData == null) {
                request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
            } else {
                if (userData.getRole() == 1) {      // member
                    request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                }
            }
        } catch (IOException | SQLException | ServletException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void validString(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
            String hotelName = request.getParameter("hotelName").trim();
            if (hotelName.length() == 0) {
                request.setAttribute("error", "Cannot find any hotel");
                if (userData == null) {
                    request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
                } else {
                    if (userData.getRole() == 1) {      // member
                        request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                    }
                }
            }
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void validListHotel(ArrayList<HotelDTO> listHotel, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
        if (listHotel.isEmpty() || listHotel == null) {
            request.setAttribute("error", "Cannot find any hotel");
            if (userData == null) {
                request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
            } else {
                if (userData.getRole() == 1) {      // member
                    request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                }
            }
        }
    }

}
