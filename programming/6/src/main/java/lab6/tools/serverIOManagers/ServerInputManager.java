package lab6.tools.serverIOManagers;

import lab6.commands.Command;
import lab6.excepcions.InvalidArgumentException;
import lab6.excepcions.InvalidCommandException;
import lab6.excepcions.InvalidRequestException;
import lab6.excepcions.MyException;
import lab6.tools.RequestBox;
import lab6.tools.UDPConnection;
import lab6.tools.Serializer;
import lab6.tools.ClientRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class ServerInputManager {
    private UDPConnection udpConnection;
    private ServerOutputManager outputManager;
    private RequestBox<ClientRequest> request;
    private RequestBox<ClientRequest> lastRequest;
    private LinkedList<RequestBox<ClientRequest>> requests;
    private static final Logger log4j2 = LogManager.getLogger();


    public ServerInputManager(ServerOutputManager serverOutputManager, UDPConnection udpConnection) {
        this.outputManager = serverOutputManager;
        this.udpConnection = udpConnection;
        requests = new LinkedList<>();
    }

    public ClientRequest getClientRequest(HashMap<String, Command> commands) throws MyException {
        log4j2.debug("СПИСОК ЗАПРОСОВ: " + requests);
        ClientRequest clientRequest;
        while (true) {
            clientRequest = (ClientRequest) Serializer.deserialize(udpConnection.receiveData());

            if (clientRequest == null) throw new InvalidRequestException("null");

            request = new RequestBox<>(clientRequest, udpConnection.getLastReceivedAddress());

            if (request.equals(lastRequest)) continue;
            lastRequest = request;
            log4j2.debug("НОВЫЙ ЗАПРОС: " + request);
            break;
        }

        if (clientRequest.getName() == null){
            Optional<RequestBox<ClientRequest>> optionalRequest = requests.stream().filter(request -> request.getAddress().equals(this.request.getAddress())).findFirst();
            if (optionalRequest.isPresent()) request = optionalRequest.get();
            else throw new MyException("Технические шоколадки сервера", "команда не может быть выполнена");
            requests.remove(request);
            request.getData().setElement(clientRequest.getElement());
            outputManager.setAddressToSend(request.getAddress());
        }
        else {
            if (!commands.containsKey(clientRequest.getName()))
                throw new InvalidCommandException(clientRequest.getName());

            Command command = commands.get(clientRequest.getName());

            if (command.getNumberOfArguments() != -1 &&
                    clientRequest.getArguments().length != command.getNumberOfArguments())
                throw new InvalidArgumentException();
        }


        return request.getData();
    }

    public void takeElement() throws MyException {
        requests.addLast(request);
        log4j2.debug("takeElement" + requests);
        outputManager.setNeedElement();
        outputManager.sendServerRequestToClient();
    }
}
