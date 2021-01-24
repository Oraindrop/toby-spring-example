package io.github.oraindrop.dao;

import io.github.oraindrop.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("insert into user (id, name, password) values (?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws SQLException, ClassNotFoundException {
        return this.jdbcTemplate.queryForObject("select * from user where id = ?", new Object[]{id},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                });
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("delete from user");
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        return this.jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }
}
