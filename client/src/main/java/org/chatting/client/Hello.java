package org.chatting.client;

public class Hello {

    public void sayHello() {
        System.out.print("Saying hello from class org.chatting.client.Hello\n");
    }

    public void printString(String a) {
        System.out.printf("Printing string %s\n", a);
    }

    public void printNumber(int a) {
        System.out.printf("Printing number %d\n", a);
    }

    public int randomNumber(int max) {
        return (int) Math.floor(Math.random() * max);
    }
}
