package excepcions;

public class NullException extends InvalidEnterException{
    public NullException(String fieldName){
        super(fieldName, "Поле не может быть null");
    }
}
