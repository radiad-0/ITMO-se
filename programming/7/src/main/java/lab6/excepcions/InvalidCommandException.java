package lab6.excepcions;

public class InvalidCommandException extends MyException {
    public InvalidCommandException(String command) {
        super("Неверная команда " + command, "\nПо команде help можно получить список доступных команд.");
    }
}
