package com.kuaihuoyun.akaboot.spring.remote;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class RemoteServiceRegistryCollector {

    private BeanDefinitionRegistry registry;

    public  RemoteServiceRegistryCollector(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public boolean tryCollecting(Class<?> clz) {
       //todo 根据注解 @AkkaRemoteService 判断收集规则

        return false;

    }

}
