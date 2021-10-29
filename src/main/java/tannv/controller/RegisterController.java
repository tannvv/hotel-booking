/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tannv.account.AccountDAO;
import tannv.account.AccountDTO;
import tannv.account.RegisterError;
import tannv.infor_user.InforUserDAO;
import tannv.infor_user.InforUserDTO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private static final String REGISTER_PAGE = "registerPage";
    private static final String REGISTER_ERROR_PAGE = "registerPageError";
    private static final String LOGIN_PAGE = "loginPageError";
    private static final String INVALID_PAGE = "invalidPage";
    private final Logger LOGGER = Logger.getLogger(RegisterController.class);


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect(REGISTER_PAGE);
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
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("rePassword");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            AccountDTO newAcc = new AccountDTO(email, password);
            InforUserDTO newUser = new InforUserDTO(email, name, phone, address);
            RegisterError error =  RegisterError.checkValidAccount(newAcc, newUser, rePassword);
            
            if (error.getEmail() == null && error.getPassword() == null && error.getRePassword() == null
                 && error.getName() == null && error.getAddress()==null && error.getPhone() == null   ) { // vali infor
                AccountDAO accountDAO = new AccountDAO();
                accountDAO.createAccount(newAcc);
                InforUserDAO inforDAO = new InforUserDAO();
                inforDAO.createInfor(newUser);
                request.setAttribute("notifi", "Create account success");
                RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
                rd.forward(request, response);
            } else {              // invalid infor.. return the register page
                request.setAttribute("error", error);
                request.setAttribute("newAcc", newAcc);
                request.setAttribute("newUser", newUser);
                request.setAttribute("rePassword", rePassword);
                RequestDispatcher rd = request.getRequestDispatcher(REGISTER_ERROR_PAGE);
                rd.forward(request, response);
            }
        } catch (IOException | SQLException | NamingException | ServletException ex) {
            //LOGGER.error("Create article" + ex.getMessage());
           LOGGER.error(ex.toString());
            response.sendRedirect(INVALID_PAGE);
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
