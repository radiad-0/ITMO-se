package lab6.tools.clientIOManagers;

import lab6.excepcions.MyException;
import lab6.tools.*;
import lab6.tools.serverIOManagers.ServerRequest;

import java.net.SocketAddress;

/**
 * Класс, выводящий сообщения пользователю
 */
public class ClientOutputManager implements OutputManager {
    private UDPConnection udpConnection;
    private SocketAddress serverAddress;
    ShutdownHook shutdownHook;
    private Boolean manualMode;

    /**
     * конструктор
     * @param udpConnection UDP соединение {@link UDPConnection}
     * @param serverAddress адрес сервера
     */
    public ClientOutputManager(UDPConnection udpConnection, SocketAddress serverAddress) {
        this.serverAddress = serverAddress;
        manualMode = true;
        this.udpConnection = udpConnection;
        shutdownHook = new ShutdownHook(this);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * посылает запрос серверу
     * @param clientRequest сформированный запрос
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void sendClientRequest(ClientRequest clientRequest) throws MyException {
        udpConnection.sendData(Serializer.serialize(clientRequest), serverAddress);
    }

    /**
     * выделанные
     * @param message
     * @return
     */
    public String highlightedStyle(Object message){
        return "\033[35m" + message + "\033[0m";
    }

    public String errorStyle(Object message){
        return "\033[31m" + message + "\033[0m";
    }

    public void printServerRequest(ServerRequest serverRequest) {
        if (serverRequest.getMessage() != null) System.out.print(serverRequest.getMessage());
    }

    public void removeShutdownHook(){
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
    }

    @Override
    public void print(Object message) {
        System.out.print(message);
    }

    @Override
    public void println(Object message) {
        System.out.println(message);
    }

    public void printManualMode(Object message){
        if (manualMode) print(message);
    }

    public void printLnManualMode(Object message){
        if (manualMode) println(message);
    }

    public void printScriptMode(Object message){
        if (!manualMode) print(message);
    }

    public void printLnScriptMode(Object message){
        if (!manualMode) println(message);
    }

    public void setManualMode() {
        manualMode = true;
    }

    public void setScriptMode(){
        manualMode = false;
    }

    public boolean isManualMode(){
        return manualMode;
    }

    public boolean isScriptMode(){
        return !manualMode;
    }
}
