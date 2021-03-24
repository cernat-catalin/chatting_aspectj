package org.chatting.client.aspect;

public aspect TestAspect {

    pointcut hello():
            call(* org.chatting.client.Hello.sayHello());

    before(): hello(){
        System.out.println("----- org.chatting.client.Hello Advice");
    }
}
