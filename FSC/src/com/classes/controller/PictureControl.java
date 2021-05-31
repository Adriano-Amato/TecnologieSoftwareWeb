package com.classes.controller;

import com.classes.model.bean.products.ImageBean;
import com.classes.model.dao.ImageModel;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PictureControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PictureControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = (String) request.getParameter("id");

        if (id != null) {

            ImageModel im = new ImageModel();

            try {

                ImageBean ib = im.doRetrieveByKey(id);
                byte[] image = ib.getImage();

                response.setContentType("image/jpg");
                ServletOutputStream out = response.getOutputStream();

                out.write(image);

                out.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}