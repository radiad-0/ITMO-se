package lab6.excepcions;

public class InvalidConnectException extends MyException {

    public InvalidConnectException(String description) {
        super("проблемы с соединением", description);
    }
}
