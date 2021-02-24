package io.github.oraindrop.service;

import io.github.oraindrop.proxy.TransactionHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {

    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern;
    Class<?> serviceInterface;

    @Override
    public boolean isSingleton() {
        return false;
    }

    public TxProxyFactoryBean(Object target, PlatformTransactionManager transactionManager, String pattern, Class<?> serviceInterface) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler(this.target, this.transactionManager, this.pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] { serviceInterface }, txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
