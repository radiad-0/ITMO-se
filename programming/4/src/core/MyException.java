package core;

public class MyException extends RuntimeException{
    public MyException(String name, int coordinate){
        super(name + " не может переместиться в обратном направлении на " + Integer.toString(-coordinate));
    }
}
