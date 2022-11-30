package lab6.tools;

import lab6.excepcions.MyException;

import java.io.*;

/**
 * Класс, выполняющий сериализацию и десериализацию
 */
public class Serializer {
    /**
     * Преобразовывает объект в массив байтов
     * @param obj Объект на сериализацию
     * @return Массив байтов
     * @throws MyException Ошибка сериализация
     */
    public static byte[] serialize(Object obj) throws MyException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(obj);
            return byteStream.toByteArray();
        } catch (NotSerializableException e){
            throw new MyException("сериализация", "не имплементирован интерфейс Serializable");
        } catch (IOException e) {
            throw new MyException("сериализация", e.getMessage());
        }
    }

    /**
     * Преобразовывает массив байтов в объект
     * @param bytes Массив байтов
     * @return Объект после десериализации
     * @throws MyException Ошибка десериализация
     */
    public static Object deserialize(byte[] bytes) throws MyException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return objectStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new MyException("десериализация", "класс не найден");
        } catch (IOException e) {
            throw new MyException("десериализация", e.getMessage());
        }
    }
}
