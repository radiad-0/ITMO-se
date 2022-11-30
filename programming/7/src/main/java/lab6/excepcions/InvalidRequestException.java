package lab6.excepcions;

public class InvalidRequestException extends MyException{
    public InvalidRequestException(String description) {
        super("Неверный запрос", description);
    }
}
