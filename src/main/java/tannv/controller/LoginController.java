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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tannv.infor_user.InforUserDAO;
import tannv.infor_user.InforUserDTO;
import tannv.account.AccountDAO;
import tannv.account.AccountDTO;

/**
 *
 * @author TanNV
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    public final String LOGIN_PAGE = "loginPageError";
    public final String LOADLIST = "loadListAction";
    public final String INVALID = "invalidPage";
    public final String LOGIN_PAGE_FIRST = "loginPage";
    private final Logger LOGGER = Logger.getLogger(LoginController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE_FIRST);
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AccountDTO account = new AccountDTO(email, password);
        AccountDAO dao = new AccountDAO();
        try {
            boolean login = dao.checkLogin(email, password);
            if (login) {   //login success
                InforUserDAO inforUserDAO = new InforUserDAO();
                InforUserDTO inforUser = inforUserDAO.getInforByID(email);
                AccountDAO accountDAO = new AccountDAO();
                AccountDTO userData = accountDAO.getAccountByEmail(email);
                HttpSession sesstion = request.getSession();
                sesstion.setAttribute("inforUser", inforUser);
                sesstion.setAttribute("userData", userData);
                RequestDispatcher rd = request.getRequestDispatcher(LOADLIST);
                rd.forward(request, response);
            } else {
                request.setAttribute("error", "Email or password is incrrect");
                request.setAttribute("account", account);
                RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
                rd.forward(request, response);
            }
        } catch (IOException | SQLException | NamingException | ServletException e) {
            LOGGER.error(e.toString());
            response.sendRedirect(INVALID);
            
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
