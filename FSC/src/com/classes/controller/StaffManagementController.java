package com.classes.controller;

import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.bean.staff.StaffBean;
import com.classes.model.dao.SquadraModel;
import com.classes.model.dao.StaffModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class StaffManagementController extends HttpServlet {
	
	public StaffManagementController(){
        super();
    }

	private static final String insertError = "Errore nei campi di input";
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		try {
            if(action != null) {
            	switch ( action ) {
            		case "add" :{
            			String nome = req.getParameter("nameStaff");
            			String cognome = req.getParameter("surnameStaff");
            			String ruolo = req.getParameter("ruolo");
            			String codFisc = req.getParameter("codFiscStaff");
            			String squadApp = req.getParameter("squadDest");
            			
            			//Controllo null e RegEx
            			if ( nome != null || cognome != null || ruolo != null || codFisc != null || squadApp != null ) {
            				if ( ! (nome.matches("[A-Za-z]+$") && cognome.matches("[A-Za-z]+$") && codFisc.matches("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$")) ) {
            					req.setAttribute("errorStaff", insertError);
            					req.getRequestDispatcher(req.getContextPath() + "/addStaff.jsp").forward(req, resp);
            					return;
            				}
            			}
            			else {
            				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
            	        	return;
            			}
            			
            			SquadraModel sm = new SquadraModel();
            			
            			try {
            				SquadraBean squad = sm.doRetrieveByKey(squadApp);
            				StaffModel staffM = new StaffModel();
            				
            				//Controllo se ci sono problemi di Codice fiscale
            				Collection<StaffBean> colCod = null;
            				try {
            					colCod = staffM.doRetriveAll("codFis");
            				} catch (SQLException e) {
            					e.printStackTrace();
            				}
            				Iterator<StaffBean> itCod = colCod.iterator();
            				StaffBean app = new StaffBean();
            				while ( itCod.hasNext() ) {
            					app = (StaffBean) itCod.next();
            					if (app.getCodFis().equalsIgnoreCase(codFisc) ){ 
            						req.setAttribute("errorStaff", "Codice fiscale gia inserito!");
            						req.getRequestDispatcher(req.getContextPath() + "/addStaff.jsp").forward(req, resp);
            						return;
            					}	
            				}
            				/********************************************************************************************/
            				
            				StaffBean staff = new StaffBean();
            				staff.setName(nome);
            				staff.setRole(ruolo);
            				staff.setSurname(cognome);
            				staff.setCodFis(codFisc);
            				staff.setSquadra(squad);
            				
            				staffM.doSave(staff);
            				resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");
            				return;
            			} catch (SQLException e) {
            				e.printStackTrace();
            			}
            				break;
            			}
            		case "modify" : {
            			String ruolo = req.getParameter("ruolo");
            	        String app = req.getParameter("member");
            	        StaffModel sm = new StaffModel();

            	        try {
            	            StaffBean staff = sm.doRetrieveByKey(app);
            	            SquadraModel sqm = new SquadraModel();
            	            SquadraBean squadra = sqm.doRetrieveByKey(req.getParameter("squadDest"));

            	            staff.setSquadra(squadra);
            	            staff.setRole(ruolo);

            	            sm.doUpdate(staff);
            	            resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");

            	        } catch (SQLException e) {
            	            // TODO Auto-generated catch block
            	            e.printStackTrace();
            	        }
            			break;
            		}
            		case "remove" : {
            			String app = req.getParameter("member");
            			StaffModel sm = new StaffModel();
            			try {
            	            StaffBean staff = sm.doRetrieveByKey(app);
            	            
            	            sm.doDelete(staff);
            	            resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");
            			} catch (SQLException e) {
            	            // TODO Auto-generated catch block
            	            e.printStackTrace();
            	        }
            			
            			break;
            		}
            	} 
            }
		} catch(NumberFormatException e) {
				System.out.println("Error: "+ e.getMessage());
                req.setAttribute("error", e.getMessage());
		}
		
	}

}