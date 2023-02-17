package lab6.excepcions;

public class NullException extends MyException {
    public NullException(String fieldName){
        super(fieldName, "не может быть null");
    }
}
