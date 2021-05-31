package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.SquadraBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class SquadraModel implements Model<SquadraBean> {

    private static String nomeTabella = "squadra";

    @Override
    public SquadraBean doRetrieveByKey(String code) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        SquadraBean tmp = new SquadraBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE nome = ?";

        try {

            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tmp.setName(rs.getString("nome"));
                tmp.setAge_range(rs.getInt("etabambini"));
                tmp.setCategory(rs.getString("categoria"));
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

        return tmp;
    }

    @Override
    public Collection<SquadraBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<SquadraBean> squads = new LinkedList<SquadraBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {

            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                SquadraBean tmp = new SquadraBean();
                tmp.setName(rs.getString("nome"));
                tmp.setAge_range(rs.getInt("etabambini"));
                tmp.setCategory(rs.getString("categoria"));
                squads.add(tmp);
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

        return squads;
    }

    @Override
    public void doSave(SquadraBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + " VALUES (?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, param.getName());
            preparedStatement.setInt(2, param.getAge_range());
            preparedStatement.setString(3, param.getCategory());
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
    public void doUpdate(SquadraBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET etabambini=?, categoria=?  WHERE nome = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, param.getAge_range());
            preparedStatement.setString(2, param.getCategory());
            preparedStatement.setString(3, param.getName());

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
    public void doDelete(SquadraBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE nome = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getName());

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
}
