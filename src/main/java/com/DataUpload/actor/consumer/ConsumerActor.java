package com.DataUpload.actor.consumer;

import akka.actor.UntypedActor;

/**
 * Created by tangjialiang on 2017/10/25.
 *
 */
public class ConsumerActor extends UntypedActor{

    // 该类的处理函数 handler 可能是进行多线程

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void onReceive(Object message) throws Throwable {

    }
}
