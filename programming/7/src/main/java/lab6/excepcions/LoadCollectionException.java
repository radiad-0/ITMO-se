package lab6.excepcions;

public class LoadCollectionException extends MyException{
    public LoadCollectionException(String description) {
        super("проблемы с загрузкой файла", description);
    }

    public LoadCollectionException(Exception e) {
        super("проблемы с загрузкой файла", e.getMessage());
    }
}
