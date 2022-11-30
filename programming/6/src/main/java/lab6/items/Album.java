package lab6.items;

import lab6.excepcions.MyException;
import lab6.excepcions.LessZeroException;
import lab6.excepcions.NullOrEmptyException;

import java.io.Serializable;

/**
 * Класс описывающий альбом
 */
public class Album implements Serializable {
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Значение поля должно быть больше 0
     */
    private int length;

    /**
     * @param name название альбома
     * @param length длина альбоме
     * @throws MyException Некорректные данные альбома
     */
    public Album(String name, int length) throws MyException {
        this.name = name;
        this.length = length;

        if (checkNameIsCorrect(name)) throw new NullOrEmptyException("Album.name");
        if (checkLengthIsCorrect(length)) throw new LessZeroException("Album.length");
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

    public boolean checkNameIsCorrect(String name){
        return name == null || name.isEmpty();
    }

    public boolean checkLengthIsCorrect(int length){
        return length <= 0;
    }

    @Override
    public String toString() {
        return "Album{" +
                "\n\t\tname='" + name + '\'' +
                "\n\t\tlength=" + length +
                "\n\t}";
    }
}
