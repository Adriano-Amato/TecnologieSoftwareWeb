package com.classes.model.dao;

import com.classes.controller.IscrittoNotFoundException;
import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class UserModel implements Model<User>{

    private static String nomeTabella = "utente";

    @Override
    public User doRetrieveByKey(String code) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;

        User tmp = new User();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE username = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            if(isEmpty(rs)){
                throw new IscrittoNotFoundException();
            }

            while(rs.next()){
                tmp.setUsername(rs.getString("username"));
                tmp.setPassword(rs.getString("psw"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
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
    public Collection<User> doRetriveAll(String order) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;

        Collection<User> users = new LinkedList<User>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User tmp = new User();
                tmp.setUsername(rs.getString("username"));
                tmp.setPassword(rs.getString("psw"));
                tmp.setNome(rs.getString("nome"));
                tmp.setCognome(rs.getString("cognome"));
                users.add(tmp);
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

        return users;

    }

    @Override
    public void doSave(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + " VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNome());
            preparedStatement.setString(4, user.getCognome());
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
    public void doUpdate(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET psw=?, nome=?, cognome=?  WHERE username = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getUsername());

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
    public void doDelete(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE username = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, user.getUsername());

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

    private boolean isEmpty(ResultSet rs) throws SQLException {
        if(!rs.first()){
            return true;
        }else{
            //Necessario perchè nell'if il cursore è stato spostato alla prima riga,
            //ma il successivo while vuole che il cursore sia posizionato PRIMA della
            //prima riga
            rs.previous();
            return false;
        }
    }

}
