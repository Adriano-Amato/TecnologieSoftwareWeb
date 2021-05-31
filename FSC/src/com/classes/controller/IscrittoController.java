package com.classes.controller;

import com.classes.model.dao.IscrittoModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IscrittoController extends HttpServlet {

    IscrittoModel im = new IscrittoModel();

    public IscrittoController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String destination = (String) req.getAttribute("destination");

        try {
            req.removeAttribute("iscritti"); //ripulisco l'attributo iscritti
            String order = req.getParameter("sort");
            req.setAttribute("iscritti", im.doRetriveAll(order));
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
        }

        RequestDispatcher dispatcher;
        if (destination == null) {
            dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
        } else {
            dispatcher = this.getServletContext().getRequestDispatcher(destination);
        }
        dispatcher.forward(req, resp);

    }

}
