package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.AcquistoBean;
import com.classes.model.bean.products.ProductBean;
import com.classes.model.bean.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class AcquistoModel implements Model<AcquistoBean> {

    private static String nomeTabella = "acquisto";

    @Override
    public AcquistoBean doRetrieveByKey(String code) throws SQLException {

        int codeAsInt = Integer.parseInt(code);

        Connection con = null;
        PreparedStatement ps = null;

        AcquistoBean acquisto = new AcquistoBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE codAcquisto = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, codeAsInt);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                acquisto.setQuantita(rs.getInt("quantita"));
                acquisto.setCodAcquisto(codeAsInt);

                ProductModel pm = new ProductModel();
                ProductBean tmpPb = pm.doRetrieveByKey(Integer.toString(rs.getInt("prodotto")));
                acquisto.setProdotto(tmpPb);

                UserModel um = new UserModel();
                User tmpU = um.doRetrieveByKey(rs.getString("utente"));
                acquisto.setUtente(tmpU);
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

        return acquisto;
    }

    @Override
    public Collection<AcquistoBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<AcquistoBean> acquisti = new LinkedList<AcquistoBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            ProductModel pm = new ProductModel();
            UserModel um = new UserModel();

            while(rs.next()){
                AcquistoBean tmp = new AcquistoBean();

                tmp.setCodAcquisto(rs.getInt("codAcquisto"));
                tmp.setQuantita(rs.getInt("quantita"));

                tmp.setProdotto(pm.doRetrieveByKey(Integer.toString(rs.getInt("prodotto"))));
                tmp.setUtente(um.doRetrieveByKey(rs.getString("utente")));

                acquisti.add(tmp);
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

        return acquisti;
    }

    @Override
    public void doSave(AcquistoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + "(utente,prodotto,quantita) VALUES (?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, param.getUtente().getUsername());
            preparedStatement.setInt(2, param.getProdotto().getCode());
            preparedStatement.setInt(3, param.getQuantita());
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
    public void doUpdate(AcquistoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET utente=?, prodotto=?, quantita=?  WHERE codAcquisto = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getUtente().getUsername());
            preparedStatement.setInt(2, param.getProdotto().getCode());
            preparedStatement.setInt(3, param.getQuantita());
            preparedStatement.setInt(4, param.getCodAcquisto());
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
    public void doDelete(AcquistoBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE codAcquisto = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, param.getCodAcquisto());
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

    public Collection<AcquistoBean> doRetriveByUser(String username) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;

        Collection<AcquistoBean> acquisti = new LinkedList<AcquistoBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE utente=?";

        try {

            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            ProductModel pm = new ProductModel();
            UserModel um = new UserModel();

            while(rs.next()){
                AcquistoBean tmp = new AcquistoBean();

                tmp.setCodAcquisto(rs.getInt("codAcquisto"));
                tmp.setQuantita(rs.getInt("quantita"));

                tmp.setProdotto(pm.doRetrieveByKey(Integer.toString(rs.getInt("prodotto"))));
                tmp.setUtente(um.doRetrieveByKey(rs.getString("utente")));

                acquisti.add(tmp);
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

        return acquisti;
    }

}
