package excepcions;

public class NullException extends InvalidEnterException{
    public NullException(String fieldName){
        super(fieldName, "не может быть null");
    }
}
