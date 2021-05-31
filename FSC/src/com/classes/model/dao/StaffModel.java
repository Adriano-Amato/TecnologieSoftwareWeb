package com.classes.model.dao;

import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.bean.staff.StaffBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class StaffModel implements Model<StaffBean> {

    private static String nomeTabella = "dipendenti";

    @Override
    public StaffBean doRetrieveByKey(String code) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        StaffBean tmp = new StaffBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE codFis = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tmp.setName(rs.getString("nome"));
                tmp.setSurname(rs.getString("cognome"));
                tmp.setRole(rs.getString("ruolo"));
                tmp.setCodFis(rs.getString("codFis"));
                SquadraModel sm = new SquadraModel();
                SquadraBean tmpSquad = sm.doRetrieveByKey(rs.getString("squadra"));
                tmp.setSquadra(tmpSquad);
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
    public Collection<StaffBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<StaffBean> staffList = new LinkedList<StaffBean>();

        SquadraModel sm = new SquadraModel();
        SquadraBean tmpSquad = null;

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                StaffBean tmp = new StaffBean();
                tmp.setName(rs.getString("nome"));
                tmp.setSurname(rs.getString("cognome"));
                tmp.setRole(rs.getString("ruolo"));
                tmp.setCodFis(rs.getString("codFis"));
                tmpSquad = sm.doRetrieveByKey(rs.getString("squadra"));
                tmp.setSquadra(tmpSquad);
                staffList.add(tmp);
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

        return staffList;
    }

    @Override
    public void doSave(StaffBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + " VALUES (?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, param.getCodFis());
            preparedStatement.setString(2, param.getName());
            preparedStatement.setString(3, param.getSurname());
            preparedStatement.setString(4, param.getRole());
            preparedStatement.setString(5, param.getSquadra().getName());
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
    public void doUpdate(StaffBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET nome=?, cognome=?, ruolo=?, squadra=?  WHERE codFis = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getName());
            preparedStatement.setString(2, param.getSurname());
            preparedStatement.setString(3, param.getRole());
            preparedStatement.setString(4, param.getSquadra().getName());
            preparedStatement.setString(5, param.getCodFis());
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
    public void doDelete(StaffBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE codFis = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getCodFis());
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
