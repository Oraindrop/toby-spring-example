package io.github.oraindrop.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.oraindrop.advice.TransactionAdvice;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.dao.UserJdbcDao;
import io.github.oraindrop.service.*;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
    public ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(this.userServiceImpl());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new TransactionAdvice(new DataSourceTransactionManager(this.dataSource()))));
        return pfBean;
    }

    @Bean
    public UserService userService() {
        return (UserService) this.proxyFactoryBean().getObject();
    }

}
