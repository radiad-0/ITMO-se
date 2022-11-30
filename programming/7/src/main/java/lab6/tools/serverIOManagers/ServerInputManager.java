package lab6.tools.serverIOManagers;

import lab6.commands.Server.Command;
import lab6.excepcions.*;
import lab6.tools.UDPConnection;
import lab6.tools.Serializer;
import lab6.tools.clientIOManagers.ClientRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerInputManager {
    private UDPConnection udpConnection;
    private ServerOutputManager outputManager;
    private SocketAddress clientAddress;
    private Map<String, Command> availableCommands;
    private Map<SocketAddress, ClientRequest> requests;
    private static final Logger log4j2 = LogManager.getLogger();

    /**
     * Конструктор
     * @param serverOutputManager средство вывода данных на сервере {@link ServerOutputManager}
     * @param udpConnection UDP соединение {@link UDPConnection}
     */
    public ServerInputManager(ServerOutputManager serverOutputManager, UDPConnection udpConnection, Map<String, Command> availableCommands) {
        outputManager = serverOutputManager;
        this.udpConnection = udpConnection;

        requests = Collections.synchronizedMap(new HashMap<>());
        this.availableCommands = availableCommands;
    }

    /**
     * Передает серверу на выполнение проверенные команды(запросы клиента), полученные из очереди, если она пуста - по udp соединению
     * @return параметры команд {@link ClientRequest}
     * @throws MyException ошибка десериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized ClientRequest getClientRequest() throws MyException {
        log4j2.debug("СПИСОК ЗАПРОСОВ: " + requests);
        ClientRequest clientRequest;
        while (true) {
            clientRequest = (ClientRequest) Serializer.deserialize(udpConnection.receiveData());

            if (clientRequest == null) throw new InvalidRequestException("null");

            clientAddress = udpConnection.getLastReceivedAddress();
            clientRequest.setClientAddress(clientAddress);

            if (requests.containsKey(clientAddress) && requests.get(clientAddress).equals(clientRequest)) continue;
            log4j2.info(requests.containsKey(clientAddress) + " " + requests);

            log4j2.debug("НОВЫЙ ЗАПРОС(" + clientAddress + "): " + clientRequest);
            break;
        }

        if (clientRequest.getName() == null){
            if (!requests.containsKey(clientAddress)){
                requests.put(clientAddress, clientRequest);
                throw new MyException("Технические шоколадки", "команда не может быть выполнена");
            }
            requests.get(clientAddress).setElement(clientRequest.getElement());
            clientRequest = requests.replace(clientAddress, clientRequest);
            outputManager.setAddressToSend(clientAddress);
        }
        else {
            requests.put(clientAddress, clientRequest);
            if (!availableCommands.containsKey(clientRequest.getName()))
                throw new InvalidCommandException(clientRequest.getName());

            Command command = availableCommands.get(clientRequest.getName());

            if (command.getNumberOfArguments() != -1 &&
                    clientRequest.getArguments().length != command.getNumberOfArguments())
                throw new InvalidArgumentException();
        }

        return clientRequest;
    }

    /**
     * Вызывается если команде необходим элемент для исполнения и его нет
     * посылает запрос нового элемента клиенту и добавляет невыполненную команду в очередь, где она ожидает пока не получит элемент от клиента
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public synchronized void getElement() throws MyException {
        log4j2.debug("in_getElement: " + requests);
        outputManager.setNeedElement();
        outputManager.sendServerRequestToClient();
    }

    public synchronized SocketAddress getClientAddress() {
        return clientAddress;
    }
}
