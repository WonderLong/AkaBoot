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

package com.kuaihuoyun.akaboot.bootstrap;

import akka.actor.ActorSystem;
import com.kuaihuoyun.akaboot.core.bean.Lifecycle;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Properties;

/**
 * Bootstrap抽象了初始化一个AkkaSystem最基本的处理方法；
 * 提供了显示的生命周期管理入口。
 *
 * 无论是服务提供者还是使用者都应该以这个类为模板（基类）
 */

public abstract class AbstractAkkaBootstrap implements Lifecycle {

    public static final String AKKA_SYSTEM_NAME = "akka.system.name";

    private ActorSystem actorSystem;

    public AbstractAkkaBootstrap(){
        //todo 默认初始化
    }

    @Override
    public void init() {
        Properties akkaSystemProperties = getAkkaProperties();
        Config akkaConfig = ConfigFactory.parseProperties(akkaSystemProperties);
        String clientName = getPropertyValue(AKKA_SYSTEM_NAME);
        actorSystem = ActorSystem.create(clientName, ConfigFactory.load(akkaConfig));
        doActorsCreation();
    }

    protected abstract Properties getAkkaProperties();

    protected abstract String getPropertyValue(String name);

    protected abstract void doActorsCreation();

    @Override
    public void destroy() {
        actorSystem.terminate();
    }
}
