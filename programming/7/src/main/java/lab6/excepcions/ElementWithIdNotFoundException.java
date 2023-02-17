package lab6.excepcions;

public class ElementWithIdNotFoundException extends MyException {
    public ElementWithIdNotFoundException(int id) {
        super(String.valueOf(id), "элемента с этим id не существует");
    }
}
