package com.kuaihuoyun.akaboot.spring.remote;

import com.kuaihuoyun.akaboot.akka.discovery.AkkaRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class RemoteServiceRegistryCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServiceRegistryCollector.class);

    private BeanDefinitionRegistry registry;

    private String remoteServiceClientReference;


    public RemoteServiceRegistryCollector(BeanDefinitionRegistry registry, String remoteServiceClientReference){
        this.registry = registry;
        this.remoteServiceClientReference = remoteServiceClientReference;
    }

    public void tryCollecting(Class<?> clazz) {
        registerFieldDependencies(clazz);
        registerConstructorDependencies(clazz);
    }

    private void registerFieldDependencies(Class<?> clazz){
        final Field[] fields = clazz.getDeclaredFields();
        for(Field f : fields){
            if(f.isAnnotationPresent(AkkaRemoteService.class)){
                BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AkkaRemoteServiceFactoryBean.class)
                        .addConstructorArgValue(clazz)
                        .addConstructorArgReference(remoteServiceClientReference)
                        .setScope(BeanDefinition.SCOPE_SINGLETON)
                        .getBeanDefinition();
                registry.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
                LOGGER.debug("Registering class [{}] on remote reference [{}]", clazz.getSimpleName(), remoteServiceClientReference);
            }
        }
    }

    private void registerConstructorDependencies(Class<?> clazz){
        final Constructor[] constructors = clazz.getDeclaredConstructors();
        for(Constructor c : constructors){
            Parameter[] parameters = c.getParameters();
            for(Parameter p : parameters){
                if(p.isAnnotationPresent(AkkaRemoteService.class)){
                    BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AkkaRemoteServiceFactoryBean.class)
                            .addConstructorArgValue(clazz)
                            .addConstructorArgReference(remoteServiceClientReference)
                            .setScope(BeanDefinition.SCOPE_SINGLETON)
                            .getBeanDefinition();
                    registry.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
                    LOGGER.debug("Registering class [{}] on remote reference [{}]", clazz.getSimpleName(), remoteServiceClientReference);
                }
            }

        }
    }

}
