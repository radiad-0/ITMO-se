package lab6.excepcions;

public class InvalidAuthorizationException extends MyException {
    public InvalidAuthorizationException(String description) {
        super("ошибка авторизации", description);
    }
}
