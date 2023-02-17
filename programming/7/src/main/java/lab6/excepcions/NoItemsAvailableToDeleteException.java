package lab6.excepcions;

public class NoItemsAvailableToDeleteException extends DataBaseException {
    public NoItemsAvailableToDeleteException() {
        super("нет доступных вам для удаления элементов");
    }
}
