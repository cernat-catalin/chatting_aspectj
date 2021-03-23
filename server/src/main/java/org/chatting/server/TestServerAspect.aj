package org.chatting.server;

public aspect TestServerAspect {

    pointcut hello():
            call(* org.chatting.server.HelloServer.sayHello());

    before(): hello(){
        System.out.println("----- Hello From Server Advice");
    }
}
