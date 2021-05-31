package com.classes.controller;


import com.classes.model.bean.products.RettaBean;
import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.dao.IscrittoModel;
import com.classes.model.dao.RettaModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;


public class RettaPayController extends HttpServlet {

    public RettaPayController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private static final String insertError = "Errore nei campi di input";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String us = req.getParameter("sub");
    	
    	IscrittoModel im = new IscrittoModel();
    	IscrittoUserBean iscritto = new IscrittoUserBean();
    	
    	try {
			iscritto = im.doRetrieveByKey(us);
			
			String typeSub = req.getParameter("typeSub");
			String nome = req.getParameter("cname");
			String card = req.getParameter("ccnum");
			String cvv = req.getParameter("cvv");
			
			if ( nome != null && card != null && cvv != null ) {
				if ( !(nome.matches("[A-Za-z ]+$") && card.matches("(?:[0-9]{4}-){3}[0-9]{4}|[0-9]{16}$") && cvv.matches("^[0-9]{3}$")  ) ) {
					req.setAttribute("errorPayRetta", insertError);
					req.getRequestDispatcher(req.getContextPath() + "/payRetta.jsp").forward(req, resp);
					return;
				}
			} else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
		    	return;
			}
			
			RettaBean newRetta = new RettaBean();
			Calendar c = Calendar.getInstance();
			Date today = c.getTime();
			c.setTime(today);

			if (typeSub!=null && typeSub.equalsIgnoreCase("annuale")) {

				c.add(Calendar.YEAR, 1);
	        	Date nextYear = c.getTime();
	    	
	       		newRetta.setDataInizio(today);
	       		newRetta.setDataFine(nextYear);
	       		newRetta.setImporto(600);
	       		newRetta.setIscritto(iscritto);        		
	       		newRetta.setSconto(40);

	       		RettaModel rm = new RettaModel();
	        	try {
					rm.doSave(newRetta);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
	    	else if(typeSub!=null&&typeSub.equalsIgnoreCase("mensile")){

				c.add(Calendar.MONTH, 1);
	    		java.util.Date nextYear = c.getTime();
	    		
	    		newRetta.setDataInizio(today);
	    		newRetta.setDataFine(nextYear);
	    		newRetta.setImporto(50);
	    		newRetta.setIscritto(iscritto);
	    		newRetta.setSconto(30);

	    		RettaModel rm = new RettaModel();
	    		try {
					rm.doSave(newRetta);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			resp.sendRedirect("/personalPage.jsp");
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}