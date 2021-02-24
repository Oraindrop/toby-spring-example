package io.github.oraindrop.dao;

import com.zaxxer.hikari.HikariDataSource;
import io.github.oraindrop.service.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new UserLevelUpgradePolicyDefault(this.userDao());
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(this.userDao(), this.userLevelUpgradePolicy());
    }

    @Bean
    public TxProxyFactoryBean txProxyFactoryBean() {
        return new TxProxyFactoryBean(this.userServiceImpl(), new DataSourceTransactionManager(this.dataSource()), "upgradeLevels", UserService.class);
    }
    @Bean
    public UserService userService() throws Exception {
        return (UserService) txProxyFactoryBean().getObject();
    }


}
