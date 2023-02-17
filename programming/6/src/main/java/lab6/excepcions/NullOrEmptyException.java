package lab6.excepcions;

public class NullOrEmptyException extends MyException {
    public NullOrEmptyException(String fieldName){
        super(fieldName, "Поле не может быть null, Строка не может быть пустой");
    }
}
