package com.classes.controller;

import com.classes.model.dao.AcquistoModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*Questa Servlet viene utilizzata per acquisire tutti gli acquisti dal database*/
public class AcquistoController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AcquistoModel am = new AcquistoModel();
	
	public AcquistoController(){
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = (String) req.getAttribute("action");
		String user = (String) req.getAttribute("user");

		if (action != null && action.equals("byUser")){ //con action=byUser voglio solo gli acquisti dell'user
			if(user!=null){
				req.removeAttribute("purchase");
				try {
					req.setAttribute("purchase", am.doRetriveByUser(user));
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
			else{
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta: utente non valido");
			}
		}
		else { //senza action voglio semplicemente tutti gli acquisti
			try {
				req.removeAttribute("purchase"); //ripulisco l'attributo purchase
				req.setAttribute("purchase", am.doRetriveAll("codAcquisto"));
			} catch (SQLException e) {
				e.printStackTrace();
				req.setAttribute("error", e.getMessage());
			}
		}

		String destination = (String) req.getAttribute("destination");
		if (destination == null) {
			this.getServletContext().getRequestDispatcher("/adminPage.jsp").forward(req, resp);
		} else {
			this.getServletContext().getRequestDispatcher(destination).forward(req, resp);
		}

	}

}