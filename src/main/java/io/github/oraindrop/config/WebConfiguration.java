package io.github.oraindrop.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.oraindrop.advice.NameMatchClassMethodPointcut;
import io.github.oraindrop.advice.TransactionAdvice;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.dao.UserJdbcDao;
import io.github.oraindrop.service.*;
import io.github.oraindrop.service.test.TestUserLevelUpgradePolicy;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
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
    public UserService userService() {
        return new UserServiceImpl(this.userDao(), this.userLevelUpgradePolicy());
    }

    @Bean
    public UserService testUserService() {
        return new UserServiceImpl(this.userDao(), new TestUserLevelUpgradePolicy(this.userDao()));
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Pointcut nameMatchClassMethodPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedName("upgrade*");
        pointcut.setMappedClassName("*ServiceImpl");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor testTransactionAdvisor() {
        return new DefaultPointcutAdvisor(this.nameMatchClassMethodPointcut(), new TransactionAdvice(new DataSourceTransactionManager(this.dataSource())));
    }

}
