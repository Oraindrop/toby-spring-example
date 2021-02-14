package io.github.oraindrop.dao;

import com.zaxxer.hikari.HikariDataSource;
import io.github.oraindrop.service.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WebConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    public UserDao userDao() {
        return new UserJdbcDao(this.dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(this.userDao());
    }

}
