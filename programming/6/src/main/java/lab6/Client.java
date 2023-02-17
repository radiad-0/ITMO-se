package lab6;

import lab6.excepcions.InvalidConnectException;
import lab6.tools.ClientRequest;
import lab6.excepcions.MyException;
import lab6.tools.Serializer;
import lab6.tools.ServerRequest;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;
import lab6.tools.UDPConnection;
import lab6.tools.clientIOManagers.ShutdownHook;

import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
    private ClientOutputManager outputManager;
    private ClientInputManager inputManager;
    UDPConnection udpConnection;
    SocketAddress serverAddress;
    private static final int port = 3468;
    private boolean inRun;
    private ServerRequest lastRequest;

    /**
     * Запускает клиентское приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Client lab6 = null;
        try {
            lab6 = new Client();
            lab6.run();
        } catch (MyException e) {
            lab6.outputManager.printLnManualMode(lab6.outputManager.errorStyle(e.getMessage()));
        }
    }

    private Client() throws InvalidConnectException {
        udpConnection = new UDPConnection();
        serverAddress = new InetSocketAddress("localhost", port);
        outputManager = new ClientOutputManager(udpConnection, serverAddress);
        udpConnection.setOutputManager(outputManager);
        inputManager = new ClientInputManager(outputManager, udpConnection, new Scanner(System.in));

        inRun = true;
        outputManager.printLnManualMode(outputManager.highlightedStyle("Программа начала работу!\n" +
                "По команде help можно получить список доступных комманд."));
    }

    /**
     * Читает, обрабатывает команды и отправляет их серверу
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения
     */
    public void run() throws MyException {

        ShutdownHook shutdownHook = new ShutdownHook(udpConnection, outputManager, serverAddress);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        ClientRequest clientRequest;

        while (inRun) {
            try {
                clientRequest = inputManager.getCommandParameters();
                outputManager.sendClientRequest(clientRequest);

                processingRequest();
            } catch (MyException e) {
                outputManager.printLnManualMode(outputManager.errorStyle(e.getMessage()));
            } catch (StopSignal e){
                outputManager.printLnScriptMode(outputManager.highlightedStyle("script completed"));
                if (isScriptMode()) {
                    outputManager.sendClientRequest(new ClientRequest("exit", "endOfScript"));
                    setManualMode();
                    continue;
                }

                if (!"exit".equals(e.getMessage())) outputManager.sendClientRequest(new ClientRequest("exit", e.getMessage()));

                Runtime.getRuntime().removeShutdownHook(shutdownHook);
                inRun = false;
                inputManager.closeScanner();
                outputManager.println(outputManager.highlightedStyle("программа завершена"));
            }
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
        if (serverRequest.isSetManualMode()) setManualMode();
        if (serverRequest.isSetScriptMode()) {
            try {
                setScriptMode(new Scanner(serverRequest.getScriptFile()));
            } catch (FileNotFoundException e) {
                setManualMode();
                outputManager.sendClientRequest(new ClientRequest("exit", "endOfScript"));
                throw new MyException("Проблемы с файлом", e.getMessage());
            }
        }
    }

    public boolean isManualMode(){
        return outputManager.isManualMode();
    }

    public boolean isScriptMode(){
        return outputManager.isScriptMode();
    }

    public void setManualMode(){
        inputManager.setManualMode(new Scanner(System.in));
    }

    public void setScriptMode(Scanner scanner){
        inputManager.setScriptMode(scanner);
    }
}
