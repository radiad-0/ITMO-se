package excepcions;

public class NullOrEmptyException extends InvalidEnterException{
    public NullOrEmptyException(String fieldName){
        super(fieldName, "Поле не может быть null, Строка не может быть пустой");
    }
}
