package lab6.tools;

import lab6.excepcions.InvalidConnectException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Класс, отправляющий сериализованные данные через {@link DatagramChannel}
 */
public class UDPConnection {
    private DatagramChannel datagramChannel;
    private SocketAddress lastReceivedAddress;
    private SocketAddress lastSendAddress;
    private int port;
    private ByteBuffer lastSendBuffer;
    private OutputManager outputManager;

    public UDPConnection(int port, OutputManager outputManager) throws InvalidConnectException {
        this.port = port;
        this.outputManager = outputManager;
    }

    public UDPConnection(OutputManager outputManager) throws InvalidConnectException {
        this.outputManager = outputManager;
    }

    public UDPConnection(int port) throws InvalidConnectException {
        this.port = port;
    }

    public UDPConnection() throws InvalidConnectException {}

    {
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось открыть UPD канал");
        }
    }

    /**
     * Принимает запрос, размер буфера 10000000
     * @return массив байтов
     * @throws InvalidConnectException ошибка соединения
     */
    public byte[] receiveData() throws InvalidConnectException {
        return receiveData(10000000);
    }

    /**
     * Принимает запрос
     * @param capacity размер буфера
     * @return массив байтов
     * @throws InvalidConnectException ошибка соединения
     */
    public byte[] receiveData(int capacity) throws InvalidConnectException {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        int counter = 0;
        String message = "ждем подключения";
        try {
            Selector selector = Selector.open();

            datagramChannel.register(selector, datagramChannel.validOps());

            while(true) {
                selector.select();
                if (selector.selectedKeys().iterator().next().isReadable()) {
                    if (".".equals(message)) outputManager.println(". связь установлена");
                    lastReceivedAddress = datagramChannel.receive(buffer);
                }
                else {
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    counter++;
                    if (outputManager != null && counter % 50 == 0) {
                        outputManager.print(message);
                        message = ".";
                    }
                    sendData(lastSendBuffer, lastSendAddress);
                    continue;
                }

                return buffer.array();
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new InvalidConnectException("не удалось получить данные(" + e.getMessage() + ")");
        }
    }

    /**
     * Отправляет массив байтов по адресу последнего принятого запроса
     * @param bytesToSend массив байтов
     * @throws InvalidConnectException ошибка соединения
     */
    public void sendData(byte[] bytesToSend) throws InvalidConnectException {
        sendData(bytesToSend, lastReceivedAddress);
    }

    /**
     * Отправляет массив байтов по адресу
     * @param bytesToSend массив байтов
     * @param addressToSend адрес
     * @throws InvalidConnectException ошибка соединения
     */
    public void sendData(byte[] bytesToSend, SocketAddress addressToSend) throws InvalidConnectException {
        ByteBuffer bufferToSend = ByteBuffer.wrap(bytesToSend);
        sendData(bufferToSend, addressToSend);
    }

    /**
     * Отправляет массив байтов по адресу
     * @param bufferToSend буфер
     * @param addressToSend адрес
     * @throws InvalidConnectException ошибка соединения
     */
    public void sendData(ByteBuffer bufferToSend, SocketAddress addressToSend) throws InvalidConnectException {
        lastSendBuffer = bufferToSend;
        lastSendAddress = addressToSend;
        if (addressToSend == null) return;
        try {
            datagramChannel.send(bufferToSend, addressToSend);
            bufferToSend.clear();
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось отправить данные(" + e.getMessage() + ")");
        }
    }

    /**
     * Связывает канал с портом
     * @throws InvalidConnectException ошибка соединения
     */
    public void bindAddress() throws InvalidConnectException {
        try {
            bindAddress(new InetSocketAddress(port));
        } catch (IllegalArgumentException e){
            throw new InvalidConnectException(e.getMessage());
        }
    }

    /**
     * Связывает канал с адресом
     * @param address адрес
     * @throws InvalidConnectException ошибка соединения
     */
    public void bindAddress(SocketAddress address) throws InvalidConnectException {
        try {
            datagramChannel.bind(address);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось связать канал с адресом");
        }
    }

    public SocketAddress getLastReceivedAddress() {
        return lastReceivedAddress;
    }

    public void setOutputManager(OutputManager outputManager) {
        this.outputManager = outputManager;
    }
}
