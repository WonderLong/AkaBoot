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


package com.kuaihuoyun.akaboot.starter;


import com.kuaihuoyun.akaboot.akka.AkkaProperties;
import com.kuaihuoyun.akaboot.config.client.ConfigClient;
import com.kuaihuoyun.akaboot.config.client.ConfigClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这个configuration入口，是从业务模块范围为界限。
 * 首先是业务模块服务的系统入口；
 * 其次它需要依赖配置中心，和远程服务；
 * 无论是配置中心，还是远程服务调用都是基于Akka实现。
 *
 * @see com.kuaihuoyun.akaboot.config.client.ConfigClientProperties
 * @see com.kuaihuoyun.akaboot.config.client.ConfigClient
 */
@Configuration
@EnableConfigurationProperties({AkkaProperties.class, ConfigClientProperties.class})
public class AkkaModuleAutoConfiguration {
    //todo: 1. 获取配置属性对象
    //todo: 2. 创建配置中心代理Actor
    //todo: 3. 组装remote服务代理Actor
    //todo: 4. 本地context管理器

    /**
     *
     * @param configClientProperties
     * @return
     */

    @Bean(destroyMethod = "destroy")
    @ConditionalOnClass(ConfigClient.class)
    @ConditionalOnMissingBean
    public ConfigClient initConfigClientActor(ConfigClientProperties configClientProperties){
        return ConfigClient.builder().configClientProperties(configClientProperties).build();
    }

}
