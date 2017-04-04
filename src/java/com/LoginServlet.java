/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;
import model.Jdbc;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luke James
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * SERVLET: LOGIN SERVLET 
     * ROLE: ORHCHESTRATE COMMUNICATION BETWEEN LOGIN/HOME
     *      PAGE AND MODEL CLASS. 
     * INCLUDES: CONNECTING TO DATABASE, AUTHENTICATING USER/PASSWORD, 
     *      CREATING SESSIONS/COOKIES, FORWARDING RESULTS TO VIEW & HTTPSESSIONS.
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        response.setContentType("text/html;charset=UTF-8");

        //CREATE SESSION AND ESTABLISH CONNECTION TO DATABASE VIA LSITENER
        HttpSession session = request.getSession();
        Jdbc jdbc = new Jdbc();
        
        jdbc.connect((Connection) request.getServletContext().getAttribute("connection"));
        session.setAttribute("dbConn", jdbc);
        //HANDLE DB CONNECTION ERR!
        if ((Connection) request.getServletContext().getAttribute("connection") == null) {
            request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
        } else {

            String username = null, password = null;
            //TAKE REQUEST PARAMETERS FROM FORM AND AUTHENTICATE THROUGH DB
            username = (String) request.getParameter("username");
            password = (String) request.getParameter("pswd");
            //AUTHENTICATE USER & PASS & CHECK FOR YEARLY MEMBERSHIP PAYMENT
            String authKey = jdbc.authLogin(username, password);
            //IF AUTHENTICATION IS SUCCESSFUL, CREATE SESSION TIMEOUT, ATTRIBUTES
            //& COOKIES
            if (authKey.equals("ADMIN")) {

                session.setAttribute(username, username);
                session.setAttribute("authKey", authKey);
                //set session to expire in 20 minutes
                session.setMaxInactiveInterval(60 * 60);
                Cookie user = new Cookie("user", username);
                user.setMaxAge(60 * 60);
                response.addCookie(user);
                response.sendRedirect("loginSuccess.jsp");

            } else {
                //IF AUTHENTICATION FAILS, RETURN TO LOGIN SCREEN AND THROW ERROR MSG
                // request.getRequestDispatcher("/login.html").forward(request, response);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>Either username or password is wrong.</font>");
                rd.include(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
