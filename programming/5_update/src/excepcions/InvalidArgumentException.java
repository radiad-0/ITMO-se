package excepcions;

public class InvalidArgumentException extends InvalidEnterException{
    public InvalidArgumentException() {
        super("Неверный аргумент", "По команде help можно получить список доступных комманд.");
    }
}
