package com.classes.model.contextListener;

import com.classes.model.DriverManagerConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            DriverManagerConnectionPool.releaseConnection(con); //CREDO
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
