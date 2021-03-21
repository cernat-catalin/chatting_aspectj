public aspect TestAspect {

    pointcut hello():
            call(* Hello.sayHello());

    before(): hello(){
        System.out.println("----- Hello Advice");
    }
}
