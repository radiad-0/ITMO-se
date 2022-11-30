package excepcions;

public class ElementWithIdNotFoundException extends InvalidEnterException{
    public ElementWithIdNotFoundException(int id) {
        super(String.valueOf(id), "элемента с этим id не существует");
    }
}
