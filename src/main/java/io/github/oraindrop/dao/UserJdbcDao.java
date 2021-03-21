package io.github.oraindrop.dao;

import io.github.oraindrop.domain.Level;
import io.github.oraindrop.domain.User;
import io.github.oraindrop.service.sql.SqlService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private SqlService sqlService;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    public UserJdbcDao(DataSource dataSource, SqlService sqlService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.sqlService = sqlService;
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update(sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGet"), new Object[]{id}, this.userMapper);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query(sqlService.getSql("userGetAll"), this.userMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGetCount"), Integer.class);
    }
}
