/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tannv.account.AccountDTO;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "RequestEmailController", urlPatterns = {"/RequestEmailController"})
public class RequestEmailController extends HttpServlet {
    private final String LOGGIN_ERROR = "loginPageError";
    private final String OTP_PAGE = "otpEmailPage";
   // private final Logger LOGGER = Logger.getLogger(RequestEmailController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        AccountDTO userData = (AccountDTO) session.getAttribute("userData");
        if (userData == null) {
            request.setAttribute("error", "You cannot have permission to to this function");
            RequestDispatcher rd = request.getRequestDispatcher(LOGGIN_ERROR);
            rd.forward(request, response);
        } 
        int random = Util.getRandom();
        String email = userData.getEmail();
        session.setAttribute("otp", random);
        Util.sendEmail(email, random);
        response.sendRedirect(OTP_PAGE);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         
            
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
