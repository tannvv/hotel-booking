/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import tannv.hotel.HotelDAO;
import tannv.hotel.HotelDTO;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "SearchNumberRoom", urlPatterns = {"/SearchNumberRoom"})
public class SearchNumberRoom extends HttpServlet {

    private final String INDEX_USER_PAGE = "indexUserPage";
    private final String INDEX_GUEST_PAGE = "indexGuestPage";
        private final String LOGIN_PAGE = "loginPageError";
    private final Logger LOGGER = Logger.getLogger(SearchNumberRoom.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String numberRoom = request.getParameter("numberRoom");
        if (numberRoom == null) {
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
            int page = 0;
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ex) {
                page = 1;
                LOGGER.error("Page default = 1" + ex.getMessage());
            }
            int numberPage = hotelDAO.getPageNumberHotel();
            request.setAttribute("numberPage", numberPage);
          
            String numberRoomData = request.getParameter("numberRoom");
            request.setAttribute("numberRoom", numberRoomData);
            validDate(request,response);
            Date checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkIn"));
            Date checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkOut"));
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
            String checkInString = frm.format(checkIn);
            String checkOutString = frm.format(checkOut);
            request.setAttribute("checkInData", checkInString);
            request.setAttribute("checkOutData", checkOutString);
            
            
            validNumber(request, response);
            int numberRoom = Integer.parseInt(request.getParameter("numberRoom"));
            ArrayList<HotelDTO> listHotel = hotelDAO.getHotelByNumberRoomAndCheckInCheckOutDate(numberRoom, checkInString, checkOutString,page);
            validListHotel(listHotel, request, response);
            request.setAttribute("listHotel", listHotel);
            if (userData == null) {
                request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
            } else {
                if (userData.getRole() == 1) {      // member
                    request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                }
            }
        } catch (IOException | NumberFormatException | SQLException | ParseException | ServletException e) {
           LOGGER.error(e.getMessage());
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void validNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");

            int numberRoom = Integer.parseInt(request.getParameter("numberRoom"));
            if (numberRoom <= 0) {
                request.setAttribute("numberRoom", numberRoom);
                request.setAttribute("error", "Not result");
                if (userData == null) {
                    request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
                } else {
                    if (userData.getRole() == 1) {      // member
                        request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                    }
                }
            }
        } catch (IOException | NumberFormatException | ServletException e) {
            LOGGER.error(e.getMessage());
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
            request.setAttribute("error", "Not result");
            if (userData == null) {
                request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
            } else {
                if (userData.getRole() == 1) {      // member
                    request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                }
            }
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

    private void validDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            
            Date checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkIn"));
            Date checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("checkOut"));
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
            String checkInString = frm.format(checkIn);
            String checkOutString = frm.format(checkOut);
            request.setAttribute("checkInData", checkInString);
            request.setAttribute("checkOutData", checkOutString);
            
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
            if (Util.getNumberDay(checkIn, checkOut) >= 30 || Util.getNumberDay(checkIn, checkOut) <= 0) {
                request.setAttribute("error", "Cannot find hotels");
                if (userData == null) {
                    request.getRequestDispatcher(INDEX_GUEST_PAGE).forward(request, response);
                } else {
                    if (userData.getRole() == 1) {      // member
                        request.getRequestDispatcher(INDEX_USER_PAGE).forward(request, response);
                    }
                }
            }else{
                HttpSession session = request.getSession();
                session.setAttribute("checkIn", checkIn);
                session.setAttribute("checkOut", checkOut);
                session.setAttribute("numberDate", Util.getNumberDay(checkIn, checkOut));
            }
        } catch (IOException | ParseException | ServletException e) {
            LOGGER.error(e.getMessage());
            AccountDTO userData = (AccountDTO) request.getSession().getAttribute("userData");
            request.setAttribute("error", "Cannot find hotels");
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
