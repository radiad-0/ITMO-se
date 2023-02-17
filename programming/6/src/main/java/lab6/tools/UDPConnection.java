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

    /**
     * Конструктор, открывает {@link #datagramChannel}
     * @param port порт
     * @throws InvalidConnectException ошибка соединения
     */
    public UDPConnection(int port, OutputManager outputManager) throws InvalidConnectException {
        this.port = port;
        this.outputManager = outputManager;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось открыть UPD канал");
        }
    }

    /**
     * Конструктор, открывает {@link #datagramChannel}
     * @throws InvalidConnectException ошибка соединения
     */
    public UDPConnection(OutputManager outputManager) throws InvalidConnectException {
        this.outputManager = outputManager;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось открыть UPD канал");
        }
    }

    public UDPConnection(int port) throws InvalidConnectException {
        this.port = port;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось открыть UPD канал");
        }
    }

    public UDPConnection() throws InvalidConnectException {
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new InvalidConnectException("не удалось открыть UPD канал");
        }
    }

    public byte[] receiveData(ByteBuffer buffer) throws InvalidConnectException {
        int counter = 0;
        String message = "ждем подключения";
        try {
            Selector selector = Selector.open();

            datagramChannel.register(selector, datagramChannel.validOps());

            while(true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                SelectionKey sk = it.next();
                if (sk.isReadable()) {
                    if (".".equals(message)) outputManager.println(" связь установлена");
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
                it.remove();
                return buffer.array();

            }
        } catch (IOException e){
            throw new InvalidConnectException("не удалось получить данные");
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
     *
     * @param capacity размер буфера
     * @return массив байтов
     * @throws InvalidConnectException ошибка соединения
     */
    public byte[] receiveData(int capacity) throws InvalidConnectException {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        return receiveData(buffer);
    }

    /**
     * Принимает запрос, кладет их в массив байтов
     * @param receiveBytes массив байтов
     * @return массив байтов
     * @throws InvalidConnectException ошибка соединения
     */
    public byte[] receiveData(byte[] receiveBytes) throws InvalidConnectException {
        ByteBuffer buffer = ByteBuffer.wrap(receiveBytes);
        return receiveData(buffer);
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
            throw new InvalidConnectException("не удалось отправить данные");
        }
    }

    /**
     * Связывает канал с портом
     * @throws InvalidConnectException ошибка соединения
     */
    public void bindAddress() throws InvalidConnectException {
        bindAddress(new InetSocketAddress(port));
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
