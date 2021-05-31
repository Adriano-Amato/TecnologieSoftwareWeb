package com.classes.controller;

import com.classes.model.dao.StaffModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class StaffController extends HttpServlet {

    StaffModel sm = new StaffModel();

   public StaffController(){
       super();
   }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.removeAttribute("staff"); //ripulisco l'attributo staff
            req.setAttribute("staff", sm.doRetriveAll(""));
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
        }

        String destination = (String) req.getAttribute("destination");
        if (destination==null) {
            this.getServletContext().getRequestDispatcher("/adminPage.jsp").forward(req, resp);
        }
        else {
            this.getServletContext().getRequestDispatcher(destination).forward(req, resp);
        }
    }

}
