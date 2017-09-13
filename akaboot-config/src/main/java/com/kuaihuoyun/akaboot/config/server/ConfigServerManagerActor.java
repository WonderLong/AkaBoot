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

package com.kuaihuoyun.akaboot.config.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.kuaihuoyun.akaboot.config.message.ConfigElementChange;

public class ConfigServerManagerActor extends AbstractActor implements ConfigChangeNotify{

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);

    public Receive createReceive() {
        return receiveBuilder().matchAny(o -> log.info("received unknown message"))
                .build();
    }


    @Override
    public void notifyAllChildren(ConfigElementChange change) {

        for(ActorRef actor : getContext().getChildren()){
            //todo tell all children on specific property change
            //todo 不同child app对应的appid，env，version是不一样的，最好能更加精准推送
            actor.tell(change, self());
        }
    }
}
