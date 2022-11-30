package lab6;

import lab6.commands.Client.Command;
import lab6.commands.Client.ExecuteScript;
import lab6.commands.Client.Help;
import lab6.commands.Client.Login;
import lab6.commands.Client.Register;
import lab6.excepcions.*;
import lab6.tools.clientIOManagers.ClientRequest;
import lab6.tools.Serializer;
import lab6.tools.serverIOManagers.ServerRequest;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;
import lab6.tools.UDPConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
    private HashMap<String, Command> commands;
    private ClientOutputManager outputManager;
    private ClientInputManager inputManager;
    private static final int port = 3468;
    private boolean inRun;
    private static final Logger log4j2 = LogManager.getLogger();

    /**
     * Запускает клиентское приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        try {
            new Client(new InetSocketAddress("localhost", port)).run();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private Client(SocketAddress serverAddress) throws InvalidConnectException {
        UDPConnection udpConnection = new UDPConnection();
        outputManager = new ClientOutputManager(udpConnection, serverAddress);
        udpConnection.setOutputManager(outputManager);
        LinkedList<File> scriptsFiles = new LinkedList<>();
        inputManager = new ClientInputManager(udpConnection, outputManager, new Scanner(System.in), scriptsFiles);

        commands = new HashMap<>();

        commands.put("execute_script", new ExecuteScript(inputManager, outputManager, scriptsFiles));
        commands.put("login", new Login(inputManager, outputManager));
        commands.put("register", new Register(inputManager, outputManager));
        commands.put("help", new Help(inputManager, outputManager, commands.values()));

        inRun = true;
        outputManager.printLnManualMode(outputManager.highlightedStyle("Программа начала работу!\n" +
                "По команде help можно получить список доступных команд.\n" +
                "\n" +
                "Внимание! для работы с коллекцией необходимо пройти авторизацию:\n" +
                "команда \"register\" - создать новый аккаунт\n" +
                "команда \"login\" - войти в существующий аккаунт"));
    }

    /**
     * Читает, обрабатывает команды и отправляет их серверу
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения
     */
    public void run() throws MyException {
        ClientRequest clientRequest;

        while (inRun) {
            try {
                clientRequest = inputManager.getCommandParameters();

                if (commands.containsKey(clientRequest.getName())) {
                    Command command = commands.get(clientRequest.getName());

                    if (command.getNumberOfArguments() != -1 &&
                            clientRequest.getArguments().length != command.getNumberOfArguments())
                        throw new InvalidArgumentException();

                    command.setParameters(clientRequest);
                    if (command.execute()) continue;
                }

                if (clientRequest.getUserData() == null && !"exit".equals(clientRequest.getName())) throw new UserNotFoundException();
                outputManager.sendClientRequest(clientRequest);

                processingRequest();
            } catch (MyException e) {
                outputManager.printLnManualMode(outputManager.errorStyle(e.getMessage()));
            } catch (StopSignal e){
                outputManager.printLnScriptMode(outputManager.highlightedStyle("script completed"));
                if (isScriptMode()) {
                    setManualMode();
                    continue;
                }

                if (!"exit".equals(e.getMessage())) outputManager.sendClientRequest(new ClientRequest("exit", e.getMessage()));

                outputManager.removeShutdownHook();
                inRun = false;
                inputManager.closeScanner();
                outputManager.println(outputManager.highlightedStyle("программа завершена"));
            }
            ClientRequest.setAuthorizationMode(ClientRequest.AuthorizationMode.LOGIN);
        }
    }

    /**
     * Обработка серверного запроса
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     * @throws StopSignal Сигнал, завершающий клиентское приложение
     */
    private void processingRequest() throws MyException, StopSignal {
        ServerRequest serverRequest = inputManager.getServerRequest();

        if (serverRequest.isStopSignal()) throw new StopSignal("exit");
        if (serverRequest.isNeedElement()){
            ClientRequest clientRequest = new ClientRequest(null, inputManager.getMusicBand());
            outputManager.sendClientRequest(clientRequest);
            processingRequest();
        }
    }

    public boolean isManualMode(){
        return outputManager.isManualMode();
    }

    public boolean isScriptMode(){
        return outputManager.isScriptMode();
    }

    public void setManualMode() throws MyException {
        inputManager.setManualMode();
    }

    public void setScriptMode() throws MyException {
        inputManager.setScriptMode();
    }
}
