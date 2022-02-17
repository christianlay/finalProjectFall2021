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
@WebServlet(name = "AdminServices", urlPatterns = {"/AdminServices"})
public class AdminServices extends HttpServlet {

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
        String forward = request.getParameter("forward");
        String delete = request.getParameter("delete");
        String toggleUserType = request.getParameter("toggleUserType");
        String toggleLocked = request.getParameter("toggleLocked");
        String user = request.getParameter("user");
        DBoperations dbOps = new DBoperations();
        
        if(toggleLocked!=null){
            if(dbOps.getUserType(user).equals("0")){
                dbOps.toggleLocked(user, Integer.parseInt(toggleLocked));
                request.setAttribute("userList", dbOps.getUser());
                request.setAttribute("message", "Lock status toggled for '" + user + "'");
                request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
            }
            else{
                if(dbOps.adminUserType()){
                    if(dbOps.getUserLockStatus(user).equals("1")){
                        dbOps.toggleLocked(user, Integer.parseInt(toggleLocked));
                        request.setAttribute("userList", dbOps.getUser());
                        request.setAttribute("message", "Lock status toggled for '" + user + "'");
                        request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                    }
                    else{
                        if(dbOps.getAmountOfAdminsLocked() > 1){
                            dbOps.toggleLocked(user, Integer.parseInt(toggleLocked));
                            request.setAttribute("userList", dbOps.getUser());
                            request.setAttribute("message", "Lock status toggled for '" + user + "'");
                            request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                        }
                        else{
                            request.setAttribute("userList", dbOps.getUser());
                            request.setAttribute("message", "Cannot toggle lock status for account, must have at least one unlocked admin user!");
                            request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                        }
                        
                    }
                    
                }
                else{
                    request.setAttribute("userList", dbOps.getUser());
                    request.setAttribute("message", "Cannot toggle lock status for account, must have at least one unlocked admin user!");
                    request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                }
            }
            
        }
        
        if(toggleUserType!=null){
            if(dbOps.adminUserType()){
                dbOps.toggleUserType(user, Integer.parseInt(toggleUserType));
                request.setAttribute("userList", dbOps.getUser());
                request.setAttribute("message", "Type for account '" + user + "' toggled");
                request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
            }
            else{
                if(toggleUserType.equals("1")){
                    request.setAttribute("userList", dbOps.getUser());
                    request.setAttribute("message", "Cannot toggle acount type, must have at least one admin user!");
                    request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);    
                }
                else{
                    dbOps.toggleUserType(user, Integer.parseInt(toggleUserType));
                    request.setAttribute("userList", dbOps.getUser());
                    request.setAttribute("message", "Type for account '" + user + "' toggled");
                    request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                }
            }
            
            
        }
        
        if(delete!=null){
            if(dbOps.getUserType(delete).equals("0")){
                dbOps.deleteUser(delete);
                request.setAttribute("userList", dbOps.getUser());
                request.setAttribute("message", "Account " + delete + " deleted");
                request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
            }
            else{
                if(dbOps.adminUserType()){
                    dbOps.deleteUser(delete);
                    request.setAttribute("userList", dbOps.getUser());
                    request.setAttribute("message", "Account " + delete + " deleted");
                    request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("userList", dbOps.getUser());
                    request.setAttribute("message", "Cannot delete, must have at least one admin user!");
                    request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);  
                }
                      
            }
            
            
        }
        
        if(forward!=null){
            if(forward.equals("logout")){
                HttpSession session = request.getSession();
                session.invalidate();
                request.setAttribute("message", "Logged out");
                request.getRequestDispatcher("WEB-INF/loginPage.jsp").forward(request, response);
            }
        }
        
        request.setAttribute("userList", dbOps.getUser());
        request.getRequestDispatcher("/WEB-INF/adminPage.jsp").forward(request, response);
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
