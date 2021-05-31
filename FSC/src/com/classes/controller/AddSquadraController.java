package com.classes.controller;

import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.dao.SquadraModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class AddSquadraController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddSquadraController(){
        super();
    }
	
	private static final String insertError = "Errore nei campi di input";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String squadra = req.getParameter("squadName");
		String categoria = req.getParameter("category");
		String etapp = req.getParameter("age");
		
		//Controllo null e RegEx
		if ( squadra != null && categoria != null && etapp != null ) {
			if ( !(squadra.matches("[A-Za-z]+$") && categoria.matches("[A-Za-z ]+$") && etapp.matches("^[0-9]+$") ) ) {
				req.setAttribute("errorSquad", insertError);
				req.getRequestDispatcher(req.getContextPath() + "/addSquad.jsp").forward(req, resp);
				return;
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
	    	return;
		}
		
		int eta = Integer.valueOf( etapp );
		
		SquadraModel sm = new SquadraModel();
		SquadraBean newSquadra = new SquadraBean();

		//Controllo se ci sono problemi per il nome della squadra
		Collection<SquadraBean> colCod = null;
		try {
			colCod = sm.doRetriveAll("nome");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Iterator<SquadraBean> itCod = colCod.iterator();
		SquadraBean app = new SquadraBean();
		while ( itCod.hasNext() ) {
			app = (SquadraBean) itCod.next();
			if (app.getName().equalsIgnoreCase(squadra) ){ 
				req.setAttribute("errorSquad", "Nome di squadra già utilizzato!");
				req.getRequestDispatcher(req.getContextPath() + "/addSquad.jsp").forward(req, resp);
				return;
			}	
		}
		/********************************************************************************************/
		
		newSquadra.setAge_range(eta);
		newSquadra.setCategory(categoria);
		newSquadra.setName(squadra);
		
		try {
			sm.doSave(newSquadra);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");
        return;
	}

}
