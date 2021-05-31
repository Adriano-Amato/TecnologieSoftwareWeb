package com.classes.controller;

import com.classes.model.Carrello;
import com.classes.model.bean.entity.AcquistoBean;
import com.classes.model.bean.products.ProductBean;
import com.classes.model.bean.users.User;
import com.classes.model.dao.AcquistoModel;
import com.classes.model.dao.ProductModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarrelloController extends HttpServlet {

    public CarrelloController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private static final String insertError = "Errore nei campi di input";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductModel model = new ProductModel();

        Carrello cart = (Carrello)request.getSession().getAttribute("carrello");
        if(cart == null) {
            cart = new Carrello();
            request.getSession().setAttribute("carrello", cart);
        }

        String action = request.getParameter("action");

        try {
            if(action != null) {
                switch (action) {
                    case "addCart": {
                        String id = request.getParameter("id");
                        ProductBean bean = model.doRetrieveByKey(id);
                        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
                        if (bean != null && !bean.isEmpty()) {
                            cart.addItem(bean, quantity);
                            request.setAttribute("message", "Product " + bean.getName() + " added to cart");
                        }
                        break;
                    }
                    case "clearCart":
                        cart.deleteAll();
                        request.setAttribute("message", "Cart cleaned");
                        break;
                    case "deleteCart": {
                        String id = request.getParameter("id");
                        ProductBean bean = model.doRetrieveByKey(id);
                        if (bean != null && !bean.isEmpty()) {
                            cart.deleteItem(bean);
                            request.setAttribute("message", "Product " + bean.getName() + " deleted from cart");
                        }
                        break;
                    }
                    case "insert": {
                        String name = request.getParameter("name");
                        String description = request.getParameter("description");
                        int price = Integer.parseInt(request.getParameter("price"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        ProductBean bean = new ProductBean();
                        bean.setName(name);
                        bean.setDescription(description);
                        bean.setPrice(price);
                        bean.setQuantity(quantity);

                        model.doSave(bean);
                        request.setAttribute("message", "Product " + bean.getName() + " added");
                        break;
                    }
                    case "delete": {
                        String id = request.getParameter("id");
                        ProductBean bean = model.doRetrieveByKey(id);
                        if (bean != null && !bean.isEmpty()) {
                            model.doDelete(bean);
                            request.setAttribute("message", "Product " + bean.getName() + " deleted");
                        }
                        break;
                    }
                    case "update": {
                        String id = request.getParameter("id");
                        String name = request.getParameter("name");
                        String description = request.getParameter("description");
                        int price = Integer.parseInt(request.getParameter("price"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        ProductBean bean = new ProductBean();
                        bean.setCode(Integer.parseInt(id));
                        bean.setName(name);
                        bean.setDescription(description);
                        bean.setPrice(price);
                        bean.setQuantity(quantity);

                        model.doUpdate(bean);
                        request.setAttribute("message", "Product " + bean.getName() + " updated");
                        break;
                    }
                    case "buy": {
                        User user = (User) request.getSession().getAttribute("user");
                        if (user == null) {
                            request.setAttribute("destination", "/carrelloView.jsp");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);
                            return;
                        } else {
                        	String nome = request.getParameter("fName");
                        	String indirizzo = request.getParameter("indirizzo");
                        	String city = request.getParameter("city");
                        	String cap = request.getParameter("cap");
                        	String cname = request.getParameter("cName");
                			String card = request.getParameter("card");
                			String cvv = request.getParameter("cvv");
                			
                			if ( nome != null && card != null && cvv != null && cap != null && cname != null && city != null && indirizzo != null) {
                				if ( !(nome.matches("[A-Za-z ]+$") && card.matches("(?:[0-9]{4}-){3}[0-9]{4}|[0-9]{16}$") && cvv.matches("^[0-9]{3}$") && cap.matches("^\\d{5}$") && cname.matches("[A-Za-z ]+$") && city.matches("[A-Za-z ]+$") && indirizzo.matches("^(?:[A-Za-z]+)(?:[A-Za-z0-9 _]*)$") ) ) {
                					request.setAttribute("errorPay", insertError);
                					request.getRequestDispatcher(request.getContextPath() + "/pay.jsp").forward(request, response);
                					return;
                				}
                			} else {
                				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
                		    	return;
                			}
                        	
                            ProductModel pmodel = new ProductModel();
                            AcquistoModel amodel = new AcquistoModel();
                            HashMap<ProductBean, Integer> map = cart.getItems();
                            for (Map.Entry<ProductBean, Integer> entry : map.entrySet()) {

                                //Creo un'entità  acquisto
                                AcquistoBean abean = new AcquistoBean();
                                abean.setProdotto(entry.getKey());
                                abean.setUtente(user);
                                abean.setQuantita(entry.getValue());

                                //La salvo nel db
                                amodel.doSave(abean);

                                //Aggiorno la quantità  del prodotto disponibile nel db
                                ProductBean pbean = entry.getKey();
                                pbean.setQuantity(pbean.getQuantity() - entry.getValue());
                                pmodel.doUpdate(pbean);
                            }
                            cart.deleteAll();
                            request.getSession().setAttribute("carrello", cart);
                            response.sendRedirect(response.encodeURL(request.getContextPath() + "/ProductController"));
                            return;
                        }
                    }
                    case "pay": {
                        User user = (User) request.getSession().getAttribute("user");
                        if (user == null) {
                            request.setAttribute("destination", "/carrelloView.jsp");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);
                            return;
                        }
                        else {
                            request.getRequestDispatcher("/pay.jsp").forward(request, response);
                            return;
                        }
                    }
                    case "showCart": {
                        response.sendRedirect(response.encodeURL(request.getContextPath() + "/carrelloView.jsp"));
                        return;
                    }
                }
            }
        } catch(SQLException | NumberFormatException e) {
            System.out.println("Error: "+ e.getMessage());
            request.setAttribute("error", e.getMessage());
        }

        request.getSession().setAttribute("carrello", cart);

        this.getServletContext().getRequestDispatcher("/carrelloView.jsp").forward(request, response);

    }

}
