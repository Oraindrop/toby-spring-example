package io.github.oraindrop.service.sql;

public class StringSqlReader implements SqlReader {
    @Override
    public void read(SqlRegistry sqlRegistry) {
        sqlRegistry.registerSql("userAdd", "insert into user (id, name, password, level, login, recommend, email) values (?,?,?,?,?,?,?)");
        sqlRegistry.registerSql("userGet", "select * from user where id = ?");
        sqlRegistry.registerSql("userGetAll", "select * from user");
        sqlRegistry.registerSql("userDeleteAll", "delete from user");
        sqlRegistry.registerSql("userGetCount", "select count(*) from user");
        sqlRegistry.registerSql("userUpdate", "update user "
                + "set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ?"
                + "where id = ?");
    }
}
