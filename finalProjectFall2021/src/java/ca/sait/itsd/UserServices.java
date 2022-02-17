/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sait.itsd;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UserServices", urlPatterns = {"/UserServices"})
public class UserServices extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String forward = request.getParameter("forward");
        String registerUsername = request.getParameter("registerUsername");
        String registerPassword = request.getParameter("registerPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String newUsername = request.getParameter("newUsername");
        String confirmNewUsername = request.getParameter("confirmNewUsername");
        DBoperations dbOps = new DBoperations();
        
        if(forward!=null){
            if(forward.equals("register")){
                request.getRequestDispatcher("WEB-INF/registerPage.jsp").forward(request, response);
            }
            else if(forward.equals("login")){
                request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
            }
            else if(forward.equals("logout")){
                HttpSession session = request.getSession();
                session.invalidate();
                request.setAttribute("message", "Logged out");
                request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
            }
            else if(forward.equals("changeUsername")){
                request.setAttribute("displayChange", "block");
                request.getRequestDispatcher("WEB-INF/mainPage.jsp").forward(request, response);
            }
        }
        
        if(newUsername!=null || confirmNewUsername!=null){
            if(newUsername.equals("") || confirmNewUsername.equals("")){
                request.setAttribute("message", "Both username values are required!");
                request.setAttribute("displayChange", "block");
                request.getRequestDispatcher("WEB-INF/mainPage.jsp").forward(request, response);
            }
            else if(!newUsername.equals(confirmNewUsername)){
                request.setAttribute("message", "Usernames do not match!");
                request.setAttribute("displayChange", "block");
                request.getRequestDispatcher("WEB-INF/mainPage.jsp").forward(request, response);
            }
            else{
                HttpSession session = request.getSession();
                dbOps.updateUsername(newUsername, (String)session.getAttribute("username"));
                request.setAttribute("changedmessage", "Username changed");
                request.setAttribute("displayChange", "none");
                request.getRequestDispatcher("WEB-INF/mainPage.jsp").forward(request, response);
            }
        }
        
        if(registerUsername!=null && registerPassword!=null){
            if(registerUsername.equals("") || registerPassword.equals("") || confirmPassword.equals("")){
                request.setAttribute("message", "All values are required!");
                request.getRequestDispatcher("WEB-INF/registerPage.jsp").forward(request, response);
            }
            else if (!registerPassword.equals(confirmPassword)){
                request.setAttribute("message", "Passwords do not match!");
                request.getRequestDispatcher("WEB-INF/registerPage.jsp").forward(request, response);
            }
            else{
                dbOps.addUsernamePassword(registerUsername,registerPassword);
                request.setAttribute("message", "New account created, please log in");
                request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
            }
        }
        
        if(username==null && password==null){
            request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
        }
        else if(username.equals("") || password.equals("")){
            request.setAttribute("message", "Both username and password are required!");
            request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
        }
        else{
            if(dbOps.compareUsernamePassword(username, password)){
                if(dbOps.getUserLockStatus(username).equals("0")){
                    if(dbOps.getUserType(username).equals("0")){
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        request.setAttribute("displayChange","none");
                        request.getRequestDispatcher("WEB-INF/mainPage.jsp").forward(request, response);
                    }
                    else{
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        request.setAttribute("userList", dbOps.getUser());
                        request.getRequestDispatcher("WEB-INF/adminPage.jsp").forward(request, response);
                    }
                }
                else{
                    request.setAttribute("message", "Account is locked, contact system administrator");
                    request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
                }
                
                
            }
            else{
                request.setAttribute("message", "Invalid username or password!");
                request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
            }
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
