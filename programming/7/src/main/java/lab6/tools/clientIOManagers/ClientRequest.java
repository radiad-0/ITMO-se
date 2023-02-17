package lab6.tools.clientIOManagers;

import lab6.items.MusicBand;
import lab6.tools.UserData;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Objects;

/**
 * Класс параметров команды
 */
public class ClientRequest implements Serializable {
    private String name;
    private String[] arguments;
    private MusicBand element;
    private UserData userData;
    private SocketAddress clientAddress;
    private AuthorizationMode authorizationMode;
    private static UserData defaultUserData;
    private static AuthorizationMode defaultAuthorizationMode;
    private final long id;
    private static long idPointer;

    {
        id = ++idPointer;
        userData = defaultUserData;
        authorizationMode = defaultAuthorizationMode;
    }

    public ClientRequest(String name, String... arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public ClientRequest(String name, MusicBand element, String... arguments) {
        this.name = name;
        this.element = element;
        this.arguments = arguments;
    }

    public enum AuthorizationMode implements Serializable{
        REGISTER,
        LOGIN
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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public AuthorizationMode getAuthorizationMode() {
        return authorizationMode;
    }

    public static void setDefaultUserData(UserData userData) {
        ClientRequest.defaultUserData = userData;
    }

    public static void setAuthorizationMode(AuthorizationMode authorizationMode) {
        ClientRequest.defaultAuthorizationMode = authorizationMode;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
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
                ", userData=" + userData +
                ", authorizationMode=" + authorizationMode +
                ", id=" + id +
                '}';
    }
}
