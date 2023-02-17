package lab6.excepcions;

public class LessZeroException extends MyException {
    public LessZeroException(String fieldName){
        super(fieldName, "Значение поля должно быть больше 0");
    }
}
