package main;

public class ValueException extends Exception{
    public String message;
    public ValueException(String message){
        this.message = message;
    }
}
