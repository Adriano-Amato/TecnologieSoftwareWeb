package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.products.RettaBean;
import com.classes.model.bean.users.IscrittoUserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class RettaModel implements Model<RettaBean> {

    private static String nomeTabella = "retta";

    @Override
    public RettaBean doRetrieveByKey(String code) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;

        RettaBean tmp = new RettaBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE progressivo = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, Integer.parseInt(code));

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tmp.setImporto(rs.getInt("importo"));
                tmp.setProgressivo(Integer.parseInt(code));
                tmp.setSconto(rs.getInt("sconto"));
                tmp.setDataInizio(rs.getDate("dataInizio"));
                tmp.setDataFine(rs.getDate("dataFine"));
                
                IscrittoModel im = new IscrittoModel();
                IscrittoUserBean tmpUser = im.doRetrieveByKey(rs.getString("iscritto"));
                tmp.setIscritto(tmpUser);

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
    public Collection<RettaBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<RettaBean> rette = new LinkedList<RettaBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            IscrittoModel im = new IscrittoModel();

            while(rs.next()){
                RettaBean tmp = new RettaBean();
                tmp.setImporto(rs.getInt("importo"));
                tmp.setSconto(rs.getInt("sconto"));
                tmp.setProgressivo(rs.getInt("progressivo"));
                tmp.setDataInizio(rs.getDate("dataInizio"));
                tmp.setDataFine(rs.getDate("dataFine"));

                IscrittoUserBean tmpUser = im.doRetrieveByKey(rs.getString("iscritto"));
                tmp.setIscritto(tmpUser);

                rette.add(tmp);
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

        return rette;
    }

    @Override
    public void doSave(RettaBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO retta(importo, sconto, iscritto, dataInizio, dataFine) VALUES ( ?, ?, ?, ?, ?);";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            java.sql.Date dI = new java.sql.Date(param.getDataInizio().getTime() );
            java.sql.Date dF = new java.sql.Date(param.getDataFine().getTime() );
            
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, param.getImporto());
            preparedStatement.setInt(2, param.getSconto());
            preparedStatement.setString(3, param.getIscritto().getUsername());
            preparedStatement.setDate(4, dI);
            preparedStatement.setDate(5, dF);
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
    public void doUpdate(RettaBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET importo=?, sconto=?, iscritto=?, dataInizio=?, datafine=? WHERE progressivo = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, param.getImporto());
            preparedStatement.setInt(2, param.getSconto());
            preparedStatement.setString(3, param.getIscritto().getUsername());
            preparedStatement.setDate(4, (java.sql.Date) param.getDataInizio());
            preparedStatement.setDate(5, (java.sql.Date) param.getDataFine());
            preparedStatement.setInt(6, param.getProgressivo());
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
    public void doDelete(RettaBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE progressivo = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, param.getProgressivo());
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

    public Collection<RettaBean> doRetriveByUser(String user) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<RettaBean> rette = new LinkedList<RettaBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE utente=?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();

            IscrittoModel im = new IscrittoModel();

            while(rs.next()){
                RettaBean tmp = new RettaBean();
                tmp.setImporto(rs.getInt("importo"));
                tmp.setSconto(rs.getInt("sconto"));
                tmp.setProgressivo(rs.getInt("progressivo"));
                tmp.setDataInizio(rs.getDate("dataInizio"));
                tmp.setDataFine(rs.getDate("dataFine"));

                IscrittoUserBean tmpUser = im.doRetrieveByKey(rs.getString("iscritto"));
                tmp.setIscritto(tmpUser);

                rette.add(tmp);
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

        return rette;
    }

}
