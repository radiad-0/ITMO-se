package lab6.tools.serverIOManagers;

import lab6.excepcions.MyException;
import lab6.tools.OutputManager;
import lab6.tools.ServerRequest;
import lab6.tools.UDPConnection;
import lab6.tools.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.SocketAddress;

/**
 * Класс, формирующий и отправляющий сообщения и сигналы клиентскому приложению
 * <br>так же записывает сообщения в лог
 */
public class ServerOutputManager implements OutputManager {
    private static final Logger log4j2 = LogManager.getLogger();
    private UDPConnection udpConnection;
    private SocketAddress addressToSend;
    private boolean sendToLastAddress;
    /**
     * Запрос {@link ServerRequest} клиенту
     */
    private ServerRequest serverRequest;
    /**
     * Временное хранилище для сообщения, которое отправиться клиентскому приложению
     */
    private String buffer;
    /**
     * Поле, нужное для корректной очистки {@link #buffer}
     */
    private boolean bufferMarker;



    {
        buffer = "";
        bufferMarker = false;
        serverRequest = new ServerRequest();
        sendToLastAddress = true;
    }

    public ServerOutputManager(UDPConnection udpConnection) {
        this.udpConnection = udpConnection;
    }
    /**
     * Отправляет запрос {@link ServerRequest} клиенту, который формирует из полей {@link ServerOutputManager}
     * <br>message - копируется из {@link #buffer}
     * <br>stopSignal - устанавливается в методе {@link #setStopSignal()}
     * <br>needElement - устанавливается в методе {@link #setNeedElement()}
     * <br>manualModeOutput - устанавливается в методе {@link #setManualMode()}
     * <br>scriptMode - устанавливается в методе {@link #setScriptMode(File)} ()}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void sendServerRequestToClient() throws MyException {
        serverRequest.setMessage(buffer);
        log4j2.debug(serverRequest);
        if (sendToLastAddress) udpConnection.sendData(Serializer.serialize(serverRequest));
        else {
            udpConnection.sendData(Serializer.serialize(new ServerRequest()), addressToSend);
            sendToLastAddress = true;
        }
        buffer = "";
        bufferMarker = false;
        serverRequest = new ServerRequest();
    }

    /**
     * Установка сигнала завершения работы клиентского приложения в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public void setStopSignal(){
        serverRequest.setStopSignal(true);
    }

    /**
     * Установка сигнала требования ввода элемента в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public void setNeedElement(){
        serverRequest.setNeedElement(true);
    }

    /**
     * Установка режима вывода сообщений в консоль в ручном режиме в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public void setManualMode() {
        serverRequest.setManualMode(true);
        serverRequest.setScripMode(false);
    }

    /**
     * Установка сигнала вывода сообщений в консоль во время исполнения скрипта в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public void setScriptMode(File fileNme) {
        serverRequest.setManualMode(false);
        serverRequest.setScripMode(true);
        serverRequest.setScriptFile(fileNme);
    }

    /**
     * вызывает {@link #sendServerRequestToClient()}
     * @param message сообщение, записывается в {@link #buffer}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void printlnToClient(Object message) throws MyException {
        buffer = message + "\n";
        sendServerRequestToClient();
    }

    /**
     * вызывает {@link #sendServerRequestToClient()}
     * @param message сообщение, окрашивается в красный и записывается в {@link #buffer}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void printlnErrorToClient(Object message) throws MyException {
        buffer = "\033[31m" + message + "\033[0m" + "\n";
        sendServerRequestToClient();
    }

    /**
     *
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void noAnswer() throws MyException {
        if (sendToLastAddress) udpConnection.sendData(Serializer.serialize(new ServerRequest()));
        else {
            udpConnection.sendData(Serializer.serialize(new ServerRequest()), addressToSend);
            sendToLastAddress = true;
        }
    }

    /**
     * Записывает сообщение в {@link #buffer} с переводом на новую строку
     * @param message
     */
    public void writelnToBuffer(Object message){
        if (bufferMarker){
            buffer = "";
            bufferMarker = false;
        }
        buffer += message + "\n";
    }

    /**
     * Записывает сообщение в {@link #buffer} без перевода на новую строку
     * @param message
     */
    public void writeToBuffer(Object message){
        if (bufferMarker){
            buffer = "";
            bufferMarker = false;
        }
        buffer += message;
    }

    public void setAddressToSend(SocketAddress addressToSend) {
        this.addressToSend = addressToSend;
        sendToLastAddress = false;
    }

    @Override
    public void print(Object message) {}

    @Override
    public void println(Object message) {}
}
