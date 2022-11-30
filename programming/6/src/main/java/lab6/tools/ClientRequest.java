package lab6.tools;

import lab6.items.MusicBand;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Класс параметров команды
 */
public class ClientRequest implements Serializable {
    private String name;
    private String[] arguments;
    private MusicBand element;
    private final long id;
    private static long idPointer = 0;

    {
        id = ++idPointer;
    }

    public ClientRequest(String name, String... arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public ClientRequest(String name, MusicBand element, String... arguments) {
        this.name = name;
        this.arguments = arguments;
        this.element = element;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String... arguments) {
        this.arguments = arguments;
    }

    public MusicBand getElement() {
        return element;
    }

    public void setElement(MusicBand element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRequest that = (ClientRequest) o;
        return id == that.id && Objects.equals(name, that.name) && Arrays.equals(arguments, that.arguments) && Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, element, id);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "name='" + name + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                ", element=" + element +
                ", id=" + id +
                '}';
    }
}
