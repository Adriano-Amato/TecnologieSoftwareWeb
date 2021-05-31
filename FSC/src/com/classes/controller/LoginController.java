package com.classes.controller;

import com.classes.model.bean.users.AdminUserBean;
import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.bean.users.User;
import com.classes.model.dao.IscrittoModel;
import com.classes.model.dao.UserModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {

    User user;

    public LoginController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action.equals("logout")){
            req.getSession().removeAttribute("user");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
        else {
            //LOGIN
            String destination = req.getParameter("destination");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if(destination==null){
                destination = "/index.jsp";
            }
            if(username==null||password==null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore nei parametri della richiesta!");
                return;
            }
            if (User.isAdmin(username, password)) {
            	//ACCESSO TRAMITE ADMIN
            	req.getSession().setAttribute("user", new AdminUserBean());
            	resp.sendRedirect(req.getContextPath() + destination);
            	return;
            }
            else if (checkLogin(username, password)) {
                //SUCCESSO
                checkTypeOfUser();
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(resp.encodeURL(req.getContextPath() + destination));
                return;
            } else {
                //ERRORE
                req.setAttribute("error", true);
                req.setAttribute("destination", destination);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return true if an user with user username and pass password is found in the database, false otherwise
     */
    private boolean checkLogin(String username, String password){

        UserModel model = new UserModel();
        try {
            user = model.doRetrieveByKey(username);
            if(user.getPassword().equals(password)){
                return true;
            }
        } catch (SQLException throwables) {
            //ERRORE USERNAME
            return false;
        }
        catch (IscrittoNotFoundException throwables) {
            //ERRORE USERNAME NON NEL DATABASE E PSW VUOTA
            return false;
        }

        //ERRORE PASSWORD
        return false;

    }

    /**
     * se l'user trovato nel db è anche un iscritto, fa si che la variabile user diventi instanceof IscrittoUserBean
     * e ne ritrova le informazioni attraverso IscrittoUserModel
     */
    private void checkTypeOfUser(){
        //CONTROLLA SE E' UN ISCRITTO
        IscrittoModel iscrittoModel = new IscrittoModel();
        try {
            IscrittoUserBean bean = iscrittoModel.doRetrieveByKey(user.getUsername());
            user = bean;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IscrittoNotFoundException e){
            //Se viene lanciata vuol dire che non esiste nel db
            //un iscritto corrispondente a user
            e.printStackTrace();
        }
    }

}
