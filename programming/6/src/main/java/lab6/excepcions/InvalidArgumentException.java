package lab6.excepcions;

public class InvalidArgumentException extends MyException {
    public InvalidArgumentException() {
        super("Неверный аргумент", "По команде help можно получить список доступных комманд.");
    }
}
