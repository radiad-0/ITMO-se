package lab6.tools.serverIOManagers;

import lab6.excepcions.MyException;
import lab6.tools.OutputManager;
import lab6.tools.UDPConnection;
import lab6.tools.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /**
     * Конструктор
     * @param udpConnection UDP соединение {@link UDPConnection}
     */
    public ServerOutputManager(UDPConnection udpConnection) {
        this.udpConnection = udpConnection;

        udpConnection.setOutputManager(this);

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
    }

    {
        buffer = "";
        bufferMarker = false;
        serverRequest = new ServerRequest();
        sendToLastAddress = true;
    }

    /**
     * Отправляет запрос {@link ServerRequest} клиенту, который формирует из полей {@link ServerOutputManager}
     * <br>message - копируется из {@link #buffer}
     * <br>stopSignal - устанавливается в методе {@link #setStopSignal()}
     * <br>needElement - устанавливается в методе {@link #setNeedElement()}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized void sendServerRequestToClient() throws MyException {
        serverRequest.setMessage(buffer);
        log4j2.debug(serverRequest);
        if (sendToLastAddress) udpConnection.sendData(Serializer.serialize(serverRequest));
        else {
            udpConnection.sendData(Serializer.serialize(serverRequest), addressToSend);
            sendToLastAddress = true;
        }
        buffer = "";
        bufferMarker = false;
        serverRequest = new ServerRequest();
    }

    /**
     * Установка сигнала завершения работы клиентского приложения в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public synchronized void setStopSignal(){
        serverRequest.setStopSignal(true);
    }

    /**
     * Установка сигнала требования ввода элемента в запросе {@link ServerRequest#setStopSignal(boolean)}
     */
    public synchronized void setNeedElement(){
        serverRequest.setNeedElement(true);
    }

    /**
     * вызывает {@link #sendServerRequestToClient()}
     * @param message сообщение, записывается в {@link #buffer}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized void printlnToClient(Object message) throws MyException {
        buffer = message + "\n";
        sendServerRequestToClient();
    }

    /**
     * вызывает {@link #sendServerRequestToClient()}
     * @param message сообщение, окрашивается в красный и записывается в {@link #buffer}
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized void printlnErrorToClient(Object message) throws MyException {
        buffer = "\033[31m" + message + "\033[0m" + "\n";
        sendServerRequestToClient();
    }

    /**
     *
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized void noAnswer() throws MyException {
        if (sendToLastAddress) udpConnection.sendData(Serializer.serialize(new ServerRequest()));
        else {
            udpConnection.sendData(Serializer.serialize(new ServerRequest()), addressToSend);
            sendToLastAddress = true;
        }
    }

    /**
     * Записывает сообщение в {@link #buffer} с переводом на новую строку
     * @param message сообщение
     */
    public synchronized void writelnToBuffer(Object message){
        if (bufferMarker){
            buffer = "";
            bufferMarker = false;
        }
        buffer += message + "\n";
    }

    /**
     * Записывает сообщение в {@link #buffer} без перевода на новую строку
     * @param message сообщение
     */
    public synchronized void writeToBuffer(Object message){
        if (bufferMarker){
            buffer = "";
            bufferMarker = false;
        }
        buffer += message;
    }

    /**
     * одноразовая установка адреса клиента-получателя, после отправки (если этот метод не вызван еще раз)
     * отправка будет осуществляться по адресу последнего принятого запроса (как обычно)
     * @param addressToSend адреса клиента-получателя
     */
    public synchronized void setAddressToSend(SocketAddress addressToSend) {
        this.addressToSend = addressToSend;
        sendToLastAddress = false;
    }

    @Override
    public synchronized void print(Object message) {}

    @Override
    public synchronized void println(Object message) {}
}
