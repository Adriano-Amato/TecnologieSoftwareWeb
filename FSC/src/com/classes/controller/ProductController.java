package com.classes.controller;

import com.classes.model.bean.products.ProductBean;
import com.classes.model.dao.ProductModel;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*Questa Servlet viene utilizzata per acquisire tutti i prodotti dal database*/
public class ProductController extends HttpServlet {

    ProductModel pm = new ProductModel();

    public ProductController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String destination = (String) req.getAttribute("destination");

        if (id == null) { //no id quindi li vuole tutti
            try {
                req.removeAttribute("products"); //ripulisco l'attributo products
                String order = req.getParameter("sort");
                req.setAttribute("products", pm.doRetriveAll(order));
            } catch (SQLException e) {
                e.printStackTrace();
                req.setAttribute("error", e.getMessage());
            }

            RequestDispatcher dispatcher;
            if(destination==null){
                dispatcher = this.getServletContext().getRequestDispatcher("/productView.jsp");
            }
            else {
                dispatcher = this.getServletContext().getRequestDispatcher(destination);
            }
            dispatcher.forward(req, resp);

        } else { //è stato passato un id, creo un oggetto json con i dettagli di quel prodotto (Usato in tendina durante modifica prodotto)
            try {
                ProductBean bean = pm.doRetrieveByKey(id);
                JSONObject beanJSON = new JSONObject();
                beanJSON.put("nome", bean.getName());
                beanJSON.put("descrizione", bean.getDescription());
                beanJSON.put("quantita", bean.getQuantity());
                beanJSON.put("prezzo", bean.getPrice());
                resp.getWriter().print(beanJSON.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
