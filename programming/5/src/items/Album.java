package items;

import excepcions.InvalidEnterException;
import excepcions.LessZeroException;
import excepcions.NullOrEmptyException;

public class Album {
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Значение поля должно быть больше 0
     */
    private int length;

    /**
     * конструктор
     * @param name
     * @param length
     * @throws InvalidEnterException
     */
    public Album(String name, int length) throws InvalidEnterException {
        this.name = name;
        this.length = length;

        if (name == null || name.isEmpty()) throw new NullOrEmptyException("Album.name");
        if (length <= 0) throw new LessZeroException("Album.length");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Album{" +
                "\n\t\tname='" + name + '\'' +
                "\n\t\tlength=" + length +
                "\n\t}";
    }
}
