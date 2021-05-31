package com.classes.controller;

import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.dao.IscrittoModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeStatsController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stringGoals = req.getParameter("goals");
        String stringAssists = req.getParameter("assists");
        String stringMinutes = req.getParameter("minutes");

        int goals = Integer.valueOf(stringGoals);
        int assists = Integer.valueOf(stringAssists);
        int minutes = Integer.valueOf(stringMinutes);
        
        if( ( stringGoals == null || stringAssists == null || stringMinutes == null ) || ( goals < 0 || assists <0 || minutes < 0 ) ){
            resp.sendError(resp.SC_BAD_REQUEST, "Ci sono errori nei parametri della richiesta!");
            return;
        }

        
        String app = req.getParameter("iscritto");

        if (app == null) {
            resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");
            return;
        }

        //mi sono assicurato che app non è null
        IscrittoUserBean bean = null;
        IscrittoModel im = new IscrittoModel();

        try {
            bean = im.doRetrieveByKey(app);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int goalsOld = bean.getGoal();
        int assistsOld = bean.getAssist();
        int minutesOld = bean.getMinuti();

        bean.setGoal(goalsOld + goals);
        bean.setAssist(assistsOld + assists);
        bean.setMinuti(minutesOld + minutes);

        try {
            im.doUpdate(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/adminPage.jsp");
        return;
    }

}
