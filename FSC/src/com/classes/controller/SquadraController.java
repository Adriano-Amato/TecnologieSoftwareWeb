package com.classes.controller;

import com.classes.model.bean.staff.StaffBean;
import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.dao.IscrittoModel;
import com.classes.model.dao.SquadraModel;
import com.classes.model.dao.StaffModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SquadraController extends HttpServlet {

    public SquadraController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nomeSquadra = (String) req.getParameter("nomeSquadra");
        String order = (String) req.getParameter("sort");
        String destination = (String) req.getAttribute("destination");

        SquadraModel sm = new SquadraModel();

        if(nomeSquadra==null||nomeSquadra.equals("")){
            /*Se non è stato fornito il nome della squadra allora creo una collezione di tutte le squadre
            * e la restituisco*/
            req.removeAttribute("collezioneSquadra");
            try {
                req.setAttribute("collezioneSquadra", sm.doRetriveAll(order));
                if (destination==null) {
                    this.getServletContext().getRequestDispatcher("/squadreElenco.jsp").forward(req, resp);
                }
                else{
                    this.getServletContext().getRequestDispatcher(destination).forward(req, resp);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            /*Se è stato fornito il nome della squadra allora prendo le informazioni solo
            * della squadra specificata*/
            req.removeAttribute("squadra");
            req.removeAttribute("partecipantiSquadra");
            try {
                req.setAttribute("squadra", sm.doRetrieveByKey(nomeSquadra));
                //Recupero tutti i partecipanti alla squadra
                IscrittoModel im = new IscrittoModel();
                Collection<IscrittoUserBean> tutti = im.doRetriveAll("");
                Collection<IscrittoUserBean> partecipantiSquadra = new ArrayList<>();
                for(IscrittoUserBean i : tutti){
                    if(i.getSquadra().getName().equals(nomeSquadra)){
                        partecipantiSquadra.add(i);
                    }
                }
                req.setAttribute("partecipantiSquadra", partecipantiSquadra);
                //Recupero tutti i dipendenti che lavorano per la squadra
                StaffModel staffModel = new StaffModel();
                Collection<StaffBean> tuttiStaff = staffModel.doRetriveAll("");
                Collection<StaffBean> squadraStaff = new ArrayList<>();
                for(StaffBean bean : tuttiStaff){
                    if(bean.getSquadra().getName().equals(nomeSquadra)){
                        squadraStaff.add(bean);
                    }
                }
                req.setAttribute("staffSquadra", squadraStaff);
                RequestDispatcher rq = this.getServletContext().getRequestDispatcher("/squadraDetails.jsp");
                rq.forward(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
