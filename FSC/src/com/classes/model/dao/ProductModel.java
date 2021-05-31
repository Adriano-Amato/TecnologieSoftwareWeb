package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.products.ProductBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ProductModel implements Model<ProductBean> {
    @Override
    public ProductBean doRetrieveByKey(String code) throws SQLException {

        int codeAsInt = Integer.parseInt(code);

        Connection con = null;
        PreparedStatement ps = null;

        ProductBean product = new ProductBean();

        String selectSQL = "SELECT * FROM prodotto WHERE codId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, codeAsInt);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                product.setCode(rs.getInt("codId"));
                product.setName(rs.getString("nome"));
                product.setDescription(rs.getString("descrizione"));
                product.setPrice(rs.getInt("prezzo"));
                product.setQuantity(rs.getInt("quantita"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }

        return product;
    }

    @Override
    public Collection<ProductBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM prodotto";

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ProductBean tmp = new ProductBean();
                tmp.setCode(rs.getInt("codId"));
                tmp.setName(rs.getString("nome"));
                tmp.setDescription(rs.getString("descrizione"));
                tmp.setPrice(rs.getInt("prezzo"));
                tmp.setQuantity(rs.getInt("quantita"));
                products.add(tmp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }

        return products;
    }

    @Override
    public void doSave(ProductBean product) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO prodotto" + " VALUES (?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getCode());
            preparedStatement.setInt(5, product.getQuantity());

            preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

    }

    @Override
    public void doUpdate(ProductBean product) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE prodotto SET nome=?, descrizione=?, prezzo=?, quantita=?  WHERE codId = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCode());

            preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return;

    }

    @Override
    public void doDelete(ProductBean product) throws SQLException {

        int code = product.getCode();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM prodotto WHERE codId = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return;
    }
}
