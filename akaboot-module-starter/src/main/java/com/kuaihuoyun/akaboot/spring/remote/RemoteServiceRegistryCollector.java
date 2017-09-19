/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 WonderLong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
