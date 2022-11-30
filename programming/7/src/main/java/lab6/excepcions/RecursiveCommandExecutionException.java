package lab6.excepcions;

public class RecursiveCommandExecutionException extends MyException {
    public RecursiveCommandExecutionException(String commandName) {
        super("команда {" + commandName + "} принудительно завершена", "бесконечная рекурсия");
    }
}
