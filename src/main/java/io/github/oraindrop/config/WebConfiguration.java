package io.github.oraindrop.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.dao.UserJdbcDao;
import io.github.oraindrop.service.*;
import io.github.oraindrop.service.test.TestUserLevelUpgradePolicy;
import io.github.oraindrop.service.test.TestUserService;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class WebConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean
    public SqlService sqlService() {
        return new SimpleSqlService();
    }

    @Bean
    public UserDao userDao() {
        return new UserJdbcDao(this.dataSource(), this.sqlService());
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
    public TestUserService testUserService() {
        return new TestUserService(this.userDao(), new TestUserLevelUpgradePolicy(this.userDao()));
    }

}
