
package com.classes.controller;

import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.bean.users.User;
import com.classes.model.dao.IscrittoModel;
import com.classes.model.dao.SquadraModel;
import com.classes.model.dao.UserModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;



public class SignupController extends HttpServlet {

    public SignupController(){
        super();
    }

    private static final String insertError = "Errore sui parametri";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String typeUser = req.getParameter("typeUser");
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        Boolean checkName = nome.matches("[A-Za-z]+$");
        Boolean checkSurname = cognome.matches("[A-Za-z]+$");
        Boolean	checkUsername = username.matches("(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
        Boolean checkPassword = password.matches("(?=^.{8,}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*$");

        //Controllo null e RegEx
        if( typeUser != null || nome != null || cognome != null || username != null || password != null ){
        	if ( ! ( checkName && checkSurname  && checkUsername  && checkPassword  ) ) {
        		req.setAttribute("errorSignup", insertError);
        		req.getRequestDispatcher(req.getContextPath() + "/signup.jsp").forward(req, resp);
        		return;
        	}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
	    	return;
		}
        /********************************************************************************************/
        
        UserModel um = new UserModel();
        
        //Controllo se ci sono problemi di username
        Collection<User> col = null;
		try {
			col = um.doRetriveAll("username");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Iterator<User> it = col.iterator();
		while ( it.hasNext() ) {
			User app = it.next();
			if (app.getUsername().equalsIgnoreCase(username) ){ 
				req.setAttribute("errorSignup", "Username gia inserito!");
				req.getRequestDispatcher(req.getContextPath() + "/signup.jsp").forward(req, resp);
				return;
			}	
		}
		/********************************************************************************************/
       
        if ( typeUser.compareTo("iscritto") == 0 )  {
        	IscrittoModel im = new IscrittoModel();
        	
        	IscrittoUserBean iscritto = new IscrittoUserBean();
        	String codFisc = req.getParameter("codFisc");
        	String appSquad =  req.getParameter("squadra");
        	
        	//Controllo null e RegEx
        	if ( codFisc != null && appSquad != null) { 
        		if ( ! codFisc.matches("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$") ) {
            			req.setAttribute("errorSignup", "Errore Codice Fiscale");
            			return;
        		}
        	} else {
        		resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
    			return;
        	}
        	
        	
        	SquadraModel sm = new SquadraModel();
        	SquadraBean squad = new SquadraBean();
			try {
				squad = sm.doRetrieveByKey(appSquad);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//Controllo se ci sono problemi di Codice fiscale
			Collection<IscrittoUserBean> colCod = null;
			try {
				colCod = im.doRetriveAll("codFis");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Iterator<IscrittoUserBean> itCod = colCod.iterator();
			IscrittoUserBean app = new IscrittoUserBean();
			while ( itCod.hasNext() ) {
				app = (IscrittoUserBean) itCod.next();
				if (app.getCodFis().equalsIgnoreCase(codFisc) ){ 
					req.setAttribute("errorSignup", "Codice fiscale gia inserito!");
					req.getRequestDispatcher(req.getContextPath() + "/signup.jsp").forward(req, resp);
					return;
				}	
			}
			/********************************************************************************************/
			

        	iscritto.setAssist(0);
        	iscritto.setGoal(0);
        	iscritto.setMinuti(0);
        	iscritto.setCodFis(codFisc);
        	iscritto.setCognome(cognome);
        	iscritto.setEta(squad.getAge_range());
        	iscritto.setNome(nome);
        	iscritto.setPassword(password);
        	iscritto.setSquadra(squad);
        	iscritto.setUsername(username);
        	
        	try {
        		um.doSave(iscritto);
        	} catch (SQLException throwables) {
        		throwables.printStackTrace();
        	}
        	
        	try {
        		im.doSave(iscritto);
        	} catch (SQLException throwables) {
        		throwables.printStackTrace();
        	}

        	resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
        else {
        	User user = new User();
        	user.setNome(nome);
        	user.setCognome(cognome);
        	user.setUsername(username);
        	user.setPassword(password);

        	try {
        		um.doSave(user);
        	} catch (SQLException throwables) {
        		throwables.printStackTrace();
        	}

        	resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
