package excepcions;

public class EndOfFileException extends InvalidEnterException{
    public EndOfFileException() {
        super("NoSuchElementException", "достигнут конец файла");
    }
}
