package excepcions;

public class InvalidCommandException extends InvalidEnterException{
    public InvalidCommandException(String command) {
        super("Неверная команда " + command, "\nПо команде help можно получить список доступных комманд.");
    }
}
