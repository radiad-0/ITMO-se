package lab6.excepcions;

public class UserNotFoundException extends InvalidAuthorizationException {
    public UserNotFoundException() {
        super("пользователь не найден");
    }
}
