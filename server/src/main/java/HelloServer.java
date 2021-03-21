public class HelloServer {

    public void sayHello() {
        System.out.print("Saying hello from class Hello\n");
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
