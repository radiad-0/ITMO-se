package excepcions;

public class FileNotAccessException extends InvalidEnterException {
    public FileNotAccessException() {
        super("нет доступа к файлу", "измените настройки доступа");
    }
}
