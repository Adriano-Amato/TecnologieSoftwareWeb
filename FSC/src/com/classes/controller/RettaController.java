package com.classes.controller;

import com.classes.model.dao.RettaModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RettaController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RettaModel rm = new RettaModel();
	
	public RettaController(){
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.removeAttribute("subscription"); //ripulisco l'attributo purchase
			req.setAttribute("subscription", rm.doRetriveAll("progressivo"));
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