package io.github.oraindrop.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(new SimpleConnectionMaker());
    }
}
