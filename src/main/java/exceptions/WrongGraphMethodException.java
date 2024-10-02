package exceptions;

public class WrongGraphMethodException extends Exception{
    private final boolean isWeighted;

    public  WrongGraphMethodException(boolean isWeighted){
        this.isWeighted = isWeighted;
    }

    public String toString(){
        String message = isWeighted?
                "No ponderated method used for ponderated graph":
                "Ponderated method used for no ponderated graph";
        return getMessage() + message;
    }
}
