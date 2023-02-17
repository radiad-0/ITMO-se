package lab6.excepcions;

public class FileNotAccessException extends MyException {
    public FileNotAccessException() {
        super("нет доступа к файлу", "измените настройки доступа");
    }
}
