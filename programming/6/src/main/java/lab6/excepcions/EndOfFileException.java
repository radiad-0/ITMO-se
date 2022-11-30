package lab6.excepcions;

public class EndOfFileException extends MyException {
    public EndOfFileException() {
        super("NoSuchElementException", "достигнут конец файла");
    }
}
