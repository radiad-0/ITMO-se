package excepcions;

public class LessZeroException extends InvalidEnterException{
    public LessZeroException(String fieldName){
        super(fieldName, "Значение поля должно быть больше 0");
    }
}
