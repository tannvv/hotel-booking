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
import tannv.hotel.HotelDAO;
import tannv.hotel.HotelDTO;
import tannv.room.RoomDAO;
import tannv.room.RoomDTO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "DetailHotelController", urlPatterns = {"/DetailHotelController"})
public class DetailHotelController extends HttpServlet {
    
    private final String DETAIL_PAGE = "detailHotelPage";
    private final Logger LOGGER = Logger.getLogger(DetailHotelController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            int hotelID = Integer.parseInt(request.getParameter("hotelID"));
            HotelDAO hotelDAO = new HotelDAO();
            RoomDAO roomDAO = new RoomDAO();
            HotelDTO hotel = hotelDAO.getHotelID(hotelID);
            ArrayList<RoomDTO> listRoom = roomDAO.getRoomByHotelID(hotelID);
            request.setAttribute("hotel", hotel);
            request.setAttribute("listRoom", listRoom);
            RequestDispatcher rd = request.getRequestDispatcher(DETAIL_PAGE);
            rd.forward(request, response);
        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            LOGGER.error(e.getMessage());
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

}
