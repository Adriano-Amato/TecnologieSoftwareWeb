package com.classes.model.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface Model<T> {

    T doRetrieveByKey(String code) throws SQLException;

    Collection<T> doRetriveAll(String order) throws SQLException;

    void doSave(T param) throws SQLException;

    void doUpdate(T param) throws SQLException;

    void doDelete(T param) throws SQLException;

}
