package exceptions;

public class NullObjectReceivedException extends Exception {

    private final String receivedObject;

    public  NullObjectReceivedException(String obj){
        super("NullObjectReceivedException");
        this.receivedObject = obj;
    }

    public String toString(){
        return getMessage() + "\nNull Object Received: " + this.receivedObject;
    }
}