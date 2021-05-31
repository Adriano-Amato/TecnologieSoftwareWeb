package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.products.ImageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ImageModel implements Model<ImageBean> {

    @Override
    public ImageBean doRetrieveByKey(String code) throws SQLException {

        int codeAsInt = Integer.parseInt(code);

        Connection con = null;
        PreparedStatement ps = null;

        ImageBean image = new ImageBean();

        String selectSQL = "SELECT * FROM immagini WHERE codId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, codeAsInt);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                image.setImage(rs.getBytes("image"));
                image.setCodId( rs.getInt("codId") );
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

        return image;
    }

    @Override
    public Collection<ImageBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<ImageBean> images = new LinkedList<ImageBean>();

        String selectSQL = "SELECT * FROM immagini";

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ImageBean tmp = new ImageBean();
                tmp.setImage(rs.getBytes("image"));
                tmp.setCodId(rs.getInt("codId"));
                images.add(tmp);
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

        return images;
    }

    @Override
    public void doSave(ImageBean image) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO immagini" + " VALUES (?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setBytes(1, image.getImage());
            preparedStatement.setInt(2, image.getCodId());

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
    public void doUpdate(ImageBean image) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE immagini SET image=? WHERE codId = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setBytes(1, image.getImage());
            preparedStatement.setInt(2, image.getCodId());
     
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
    public void doDelete(ImageBean image) throws SQLException {

        int code = image.getCodId();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM immagini WHERE codId = ?";

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
