package lab6.excepcions;

public class DataBaseException extends MyException{
    public DataBaseException(String description) {
        super("работа с базой данных", description);
    }

    public DataBaseException(Exception e) {
        super("работа с базой данных", e.getMessage());
    }
}
