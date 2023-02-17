package lab6.excepcions;

public class MyException extends Exception{
    public MyException(String cause, String description ){
        super("Ошибка! " + cause + ": " + description );
    }
}
