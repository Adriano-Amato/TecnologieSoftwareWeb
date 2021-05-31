package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.bean.entity.TorneoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class TorneoModel implements Model<TorneoBean> {

    private static String nomeTabella = "torneo";

    @Override
    public TorneoBean doRetrieveByKey(String code) throws SQLException {

        String nome = code.split(",")[0];
        int edizione = Integer.parseInt(code.split(",")[1]);

        Connection con = null;
        PreparedStatement ps = null;

        TorneoBean tmp = new TorneoBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE nome = ? AND edizione = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, nome);
            ps.setInt(2, edizione);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tmp.setNome(nome);
                tmp.setEdizione(edizione);
                tmp.setPremio(rs.getString("premio"));
                SquadraModel sm = new SquadraModel();
                SquadraBean tmpBean = sm.doRetrieveByKey(rs.getString("squadra"));
                tmp.setWinner(tmpBean);
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
    public Collection<TorneoBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<TorneoBean> tornei = new LinkedList<TorneoBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            SquadraModel sm = new SquadraModel();

            while(rs.next()){
                TorneoBean tmp = new TorneoBean();
                tmp.setNome(rs.getString("nome"));
                tmp.setEdizione(rs.getInt("edizione"));
                tmp.setPremio(rs.getString("premio"));
                SquadraBean tmpBean = sm.doRetrieveByKey(rs.getString("squadra"));
                tmp.setWinner(tmpBean);
                tornei.add(tmp);
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

        return tornei;
    }

    @Override
    public void doSave(TorneoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + " VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, param.getNome());
            preparedStatement.setInt(2, param.getEdizione());
            preparedStatement.setString(3, param.getPremio());
            preparedStatement.setString(4, param.getWinner().getName());
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
    public void doUpdate(TorneoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET premio=?, squadra=? WHERE nome = ? AND edizione = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getPremio());
            preparedStatement.setString(2, param.getWinner().getName());
            preparedStatement.setString(3, param.getNome());
            preparedStatement.setInt(4, param.getEdizione());
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
    public void doDelete(TorneoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE nome = ? AND edizione = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getNome());
            preparedStatement.setInt(1, param.getEdizione());
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
