package io.github.oraindrop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:toby-spring", "sa", "");
    }
}
