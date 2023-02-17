package excepcions;

public class InvalidEnterException extends Exception{
    public InvalidEnterException(String someThing, String errorDescription ){
        super("Ошибка! " + someThing + ": " + errorDescription );
    }
}
