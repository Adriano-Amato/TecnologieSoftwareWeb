package com.classes.model.dao;

import com.classes.controller.IscrittoNotFoundException;
import com.classes.model.DriverManagerConnectionPool;
import com.classes.model.bean.entity.SquadraBean;
import com.classes.model.bean.users.IscrittoUserBean;
import com.classes.model.bean.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class IscrittoModel implements Model<IscrittoUserBean> {

    private static String nomeTabella = "iscritto";

    @Override
    public IscrittoUserBean doRetrieveByKey(String code) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        IscrittoUserBean tmp = new IscrittoUserBean();

        String selectSQL = "SELECT * FROM " + nomeTabella + " WHERE utente = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            UserModel um = new UserModel();

            if(isEmpty(rs)){
                throw new IscrittoNotFoundException();
            }

            while(rs.next()){
                User user = um.doRetrieveByKey(rs.getString("utente"));
                tmp.setNome(user.getNome());
                tmp.setCognome(user.getCognome());
                tmp.setUsername(user.getUsername());
                tmp.setPassword(user.getPassword());
                tmp.setGoal(rs.getInt("goal"));
                tmp.setAssist(rs.getInt("assist"));
                tmp.setMinuti(rs.getInt("minuti"));
                tmp.setCodFis(rs.getString("codFis"));
                tmp.setUsername(rs.getString("utente"));
                tmp.setEta(rs.getInt("eta"));

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
    public Collection<IscrittoUserBean> doRetriveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        Collection<IscrittoUserBean> users = new LinkedList<IscrittoUserBean>();

        String selectSQL = "SELECT * FROM " + nomeTabella;

        if(order!=null && !order.equals("")){
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            SquadraModel sm = new SquadraModel();
            UserModel um = new UserModel();

            while(rs.next()){
                User user = um.doRetrieveByKey(rs.getString("utente"));
                IscrittoUserBean tmp = new IscrittoUserBean();
                tmp.setNome(user.getNome());
                tmp.setCognome(user.getCognome());
                tmp.setUsername(user.getUsername());
                tmp.setPassword(user.getPassword());
                tmp.setGoal(rs.getInt("goal"));
                tmp.setAssist(rs.getInt("assist"));
                tmp.setMinuti(rs.getInt("minuti"));
                tmp.setCodFis(rs.getString("codFis"));
                tmp.setUsername(rs.getString("utente"));
                tmp.setEta(rs.getInt("eta"));
                SquadraBean tmpSquad = sm.doRetrieveByKey(rs.getString("squadra"));
                tmp.setSquadra(tmpSquad);
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
    public void doSave(IscrittoUserBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + nomeTabella + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, param.getCodFis());
            preparedStatement.setString(2, param.getUsername());
            preparedStatement.setInt(3, param.getEta());
            preparedStatement.setInt(4, param.getGoal());
            preparedStatement.setInt(5, param.getAssist());
            preparedStatement.setInt(6, param.getMinuti());
            preparedStatement.setString(7, param.getSquadra().getName());
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
    public void doUpdate(IscrittoUserBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "UPDATE " + nomeTabella + " SET codFis=?, eta=?, goal=?, assist=?, minuti=?, squadra=?  WHERE utente = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getCodFis());
            preparedStatement.setInt(2, param.getEta());
            preparedStatement.setInt(3, param.getGoal());
            preparedStatement.setInt(4, param.getAssist());
            preparedStatement.setInt(5, param.getMinuti());
            preparedStatement.setString(6, param.getSquadra().getName());
            preparedStatement.setString(7, param.getUsername());
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
    public void doDelete(IscrittoUserBean param) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + nomeTabella + " WHERE utente = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, param.getUsername());
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
