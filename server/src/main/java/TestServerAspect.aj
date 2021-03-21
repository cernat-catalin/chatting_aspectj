public aspect TestServerAspect {

    pointcut hello():
            call(* HelloServer.sayHello());

    before(): hello(){
        System.out.println("----- Hello From Server Advice");
    }
}
