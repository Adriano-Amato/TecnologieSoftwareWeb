package com.classes.controller;

import com.classes.model.bean.entity.NotiziaBean;
import com.classes.model.dao.NotiziaModel;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class NotiziaController extends HttpServlet {

    public NotiziaController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        NotiziaModel model = new NotiziaModel();

        try {

            if(req.getParameter("action")!=null&&req.getParameter("action").equals("details")){ //details
                String codice = req.getParameter("codiceNotizia");
                if (codice == null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta");
                    return;
                }
                NotiziaBean notizia = model.doRetrieveByKey(codice);
                req.setAttribute("titolo", notizia.getTitolo());
                req.setAttribute("articolo", notizia.getArticolo());
                req.getRequestDispatcher("notiziaView.jsp").forward(req, resp);
            }
            else { //home
                Collection<NotiziaBean> notizieCollection = model.doRetriveAll("codice DESC");

                ArrayList<NotiziaBean> notizie = toArrayList(notizieCollection);

                JSONObject container = new JSONObject();
                for (int i=0;i<3;i++){
                    NotiziaBean notizia = notizie.get(i);
                    JSONObject not = new JSONObject();
                    not.put("titolo", notizia.getTitolo());
                    not.put("articolo", notizia.getArticolo());
                    not.put("codice", notizia.getCodice());
                    container.put("notizia" + i, not);
                }
                resp.getWriter().print(container.toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private ArrayList<NotiziaBean> toArrayList(Collection<NotiziaBean> notizie){
        ArrayList<NotiziaBean> toBeRet = new ArrayList<>();
        for(NotiziaBean n : notizie){
            toBeRet.add(n);
        }
        return toBeRet;
    }

}
