package io.github.oraindrop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao {

    public UserDaoDeleteAll(ConnectionMaker connectionMaker) {
        super(connectionMaker);
    }

    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("delete from user");
        return ps;
    }
}
