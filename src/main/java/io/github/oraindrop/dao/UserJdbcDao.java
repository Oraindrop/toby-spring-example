package io.github.oraindrop.dao;

import io.github.oraindrop.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update("insert into user (id, name, password) values (?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public User get(String id) {
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

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from user");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }
}
