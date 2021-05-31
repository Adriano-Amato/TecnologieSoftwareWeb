package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.AcquistoBean;
import com.classes.model.bean.entity.NotiziaBean;
import com.classes.model.bean.products.ProductBean;
import com.classes.model.bean.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class NotiziaModel implements Model<NotiziaBean> {


    private static String nomeTabella = "notizia";

    @Override
    public NotiziaBean doRetrieveByKey(String code) throws SQLException {

        int codeAsInt = Integer.parseInt(code);

        Connection con = null;
        PreparedStatement ps = null;

        NotiziaBean notizia = new NotiziaBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE codice = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setInt(1, codeAsInt);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                notizia.setCodice(rs.getInt("codice"));
                notizia.setTitolo(rs.getString("titolo"));
                notizia.setArticolo(rs.getString("articolo"));
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

        return notizia;
    }

    @Override
    public Collection<NotiziaBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<NotiziaBean> notizie = new LinkedList<NotiziaBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                NotiziaBean notizia = new NotiziaBean();
                notizia.setCodice(rs.getInt("codice"));
                notizia.setTitolo(rs.getString("titolo"));
                notizia.setArticolo(rs.getString("articolo"));
                notizie.add(notizia);
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

        return notizie;
    }

    @Override
    public void doSave(NotiziaBean param) throws SQLException {

    }

    @Override
    public void doUpdate(NotiziaBean param) throws SQLException {

    }

    @Override
    public void doDelete(NotiziaBean param) throws SQLException {

    }
}
