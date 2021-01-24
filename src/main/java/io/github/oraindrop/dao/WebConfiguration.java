package io.github.oraindrop.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class WebConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource();
    }

    @Bean
    public UserDao userDao() throws SQLException {
        return new UserDao(this.dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(this.realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
