package com.classes.controller;

import com.classes.model.bean.entity.AcquistoBean;
import com.classes.model.bean.products.RettaBean;
import com.classes.model.dao.AcquistoModel;
import com.classes.model.dao.RettaModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class EntrateController extends HttpServlet {

    public EntrateController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        StaffModel sm = new StaffModel();
//        Collection<StaffBean> staff = null;
//        try {
//            staff = sm.doRetriveAll("");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        IscrittoModel im = new IscrittoModel();
//        Collection<IscrittoUserBean> iscritti = null;
//        try {
//            iscritti = im.doRetriveAll("");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        AcquistoModel am = new AcquistoModel();
        RettaModel rm = new RettaModel();
        Collection<AcquistoBean> acquisti = null;
        Collection<RettaBean> rette = null;
        try {
            acquisti = (Collection<AcquistoBean>) am.doRetriveAll("codAcquisto");
            rette = (Collection<RettaBean>) rm.doRetriveAll("iscritto");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int entrateS = 0;
        int entrateI = 0;

        if (acquisti != null && acquisti.size() > 0) {
            Iterator<?> iteratorAcquisti = acquisti.iterator();
            while (iteratorAcquisti.hasNext()) {
                AcquistoBean beanAcquisti = (AcquistoBean) iteratorAcquisti.next();
                entrateS += ((beanAcquisti.getQuantita()) * (beanAcquisti.getProdotto().getPrice()));
            }
        }

        if (rette != null && rette.size() > 0) {
            Iterator<?> iteratorRette = rette.iterator();
            while (iteratorRette.hasNext()) {
                RettaBean beanRetta = (RettaBean) iteratorRette.next();
                entrateI += (beanRetta.getImporto());
            }
        }

        req.removeAttribute("entrateS");
        req.removeAttribute("entrateI");
        req.removeAttribute("acquisti");
        req.removeAttribute("rette");
//        req.removeAttribute("iscritti");
//        req.removeAttribute("staff");

        req.setAttribute("entrateS", entrateS);
        req.setAttribute("entrateI", entrateI);
        req.setAttribute("acquisti", acquisti);
        req.setAttribute("rette", rette);
//        req.setAttribute("iscritti", iscritti);
//        req.setAttribute("staff", staff);

        this.getServletContext().getRequestDispatcher("/adminPage.jsp").forward(req, resp);

    }
}
