package lab6;

import lab6.commands.Server.*;
import lab6.excepcions.*;
import lab6.tools.*;
import lab6.tools.serverIOManagers.*;
import lab6.items.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lab6.tools.clientIOManagers.ClientRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private final Set<MusicBand> musicBands;
    private final Date date;
    private LinkedList<String> history;
    private final Map<String, Command> commands;

    private final List<ClientRequest> clients;
    private final List<SocketAddress> addresses;
    private final ExecutorService service;
    private final List<Thread> threadList;

    private final ServerInputManager inputManager;
    private final ServerOutputManager outputManager;
    private final DataBaseManager dataBaseManager;
    private static final int port = 3468;
    private static final Logger log4j2 = LogManager.getLogger();

    /**
     * Читает из переменной окружения имя файла, в котором храниться коллекция.
     * Запускает сервер
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String fileName = System.getenv("FILENAME");
        try {
            new Server(fileName).run();
        } catch (MyException e) {
            log4j2.error(e.getMessage());
        }
    }

    /**
     * @param fileName имя файла, в котором храниться коллекция
     * @throws InvalidConnectException Ошибка соединения
     */
    public Server(String fileName) throws MyException {
        log4j2.info("_________Сервер___начал___работу_________");

        musicBands = Collections.synchronizedSet(new HashSet<>());
        date = new Date();
        history = new LinkedList<>();
        commands = Collections.synchronizedMap(new HashMap<>());
        clients = Collections.synchronizedList(new LinkedList<>());
        addresses = Collections.synchronizedList(new LinkedList<>());

        service = Executors.newCachedThreadPool();
        threadList = Collections.synchronizedList(new LinkedList<>());

        UDPConnection udpConnection = new UDPConnection(port);
        udpConnection.bindAddress();
        String[] dbAuthorizationData;
        try {
            dbAuthorizationData = new Scanner(new File(System.getenv("HOME") + "/.pgpass")).nextLine().split(":");
        } catch (FileNotFoundException e) {
            throw new MyException("какие-то проблемы с доступом к файлу с паролем", e.getMessage());
        }
        dataBaseManager = new DataBaseManager("jdbc:postgresql://localhost:5432/studs", dbAuthorizationData[3], dbAuthorizationData[4]);

        outputManager = new ServerOutputManager(udpConnection);
        inputManager = new ServerInputManager(outputManager, udpConnection, commands);
        udpConnection.setOutputManager(outputManager);

        try {
            musicBands.addAll(dataBaseManager.getMusicBandsFromDataBase());
        } catch (DataBaseException e) {
            log4j2.warn(e.getMessage());
        }

        commands.put("info", new Info(outputManager, musicBands, date));
        commands.put("show", new Show(outputManager, musicBands));
        commands.put("add", new Add(outputManager, musicBands, dataBaseManager));
        commands.put("update", new Update(outputManager, musicBands, dataBaseManager));
        commands.put("remove_by_id", new RemoveById(outputManager, musicBands, dataBaseManager));
        commands.put("clear", new Clear(outputManager, musicBands, dataBaseManager));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(outputManager, musicBands, dataBaseManager));
        commands.put("remove_lower", new RemoveLower(outputManager, musicBands, dataBaseManager));
        commands.put("history", new History(outputManager, history));
        commands.put("min_by_id", new MinById(outputManager, musicBands));
        commands.put("count_less_than_genre", new CountLessThanGenre(outputManager, musicBands));
        commands.put("filter_starts_with_name", new FilterStartsWithName(outputManager, musicBands));
        commands.put("help", new Help(outputManager, commands.values()));

        log4j2.trace("конец конструктора сервера");
    }

    /**
     * Запускает основные потоки
     */
    public void run() {
        Thread thread = new Thread(this::readingRequest);
        thread.start();
        threadList.add(thread);
        service.execute(this::processingAndSendingRequest);
    }

    /**
     * Принимает запросы
     */
    private void readingRequest() {
        while (true) {
            try {
                ClientRequest clientRequest = inputManager.getClientRequest();

                Command command = commands.get(clientRequest.getName());

                if (!"exit".equals(command.getName()))
                    if (ClientRequest.AuthorizationMode.REGISTER.equals(clientRequest.getAuthorizationMode())) {
                        if (dataBaseManager.containsUser(clientRequest.getUserData()))
                            throw new InvalidRequestException("пользователь с этим именем уже существует");
                        dataBaseManager.addUser(clientRequest.getUserData());
                    } else if (!dataBaseManager.checkUserPassword(clientRequest.getUserData()))
                        throw new InvalidAuthorizationException("неверный пароль");

                if (command.isNeedElementAsArgument() && clientRequest.getElement() == null) {
                    inputManager.getElement();
                    continue;
                }

                if (clients.isEmpty()) {
                    addresses.add(clientRequest.getClientAddress());
                    clients.add(clientRequest);
                }
                else if (!addresses.contains(clientRequest.getClientAddress())) {
                    addresses.add(clientRequest.getClientAddress());
                    clients.add(clientRequest);
                    new Thread(this::readingRequest).start();
                    service.execute(this::processingAndSendingRequest);
                    log4j2.debug("новые потоки запущены");
                } else clients.add(clientRequest);

                updateHistory(clientRequest);
            } catch (MyException e) {
                try {
                    outputManager.printlnErrorToClient(e.getMessage());
                } catch (MyException ex) {
                    log4j2.error(ex.getMessage());
                }
                log4j2.debug(e.getMessage());
            }
        }
    }

    /**
     * Обрабатывает запросы и исполняет команды
     */
    private void processingAndSendingRequest() {
        while (true) {
            SocketAddress clientAddress = null;
            try {
                if (addresses.isEmpty()) continue;
                System.out.println("clientseeeee: " + addresses + ": " + clients);
                if (clients.isEmpty()) throw new StopSignal("optimization");
                ClientRequest clientRequest = clients.remove(0);
                clientAddress = clientRequest.getClientAddress();
                System.out.println("processingAndSendingRequest");

                outputManager.setAddressToSend(clientAddress);
                Command command = commands.get(clientRequest.getName());
                command.setParameters(clientRequest);
                System.out.println("command.execute");
                command.execute();
            } catch (MyException e) {
                try {
                    outputManager.printlnErrorToClient(e.getMessage());
                } catch (MyException ex) {
                    log4j2.error(ex.getMessage());
                }
                log4j2.debug(e.getMessage());
            } catch (StopSignal e) {
                if ("optimization".equals(e.getMessage())) {
                    System.out.println("optimization");
                    addresses.clear();
                    System.out.println(addresses + ": " + clients);
                    if (threadList.size() > 1) {
                        threadList.remove(0).interrupt();
                        break;
                    }
                    continue;
                }

                log4j2.info("__________Клиент___завершил___работу__________");
                addresses.remove(clientAddress);
                history.clear();

                if (e.getMessage() == null) {
                    outputManager.setStopSignal();
                    try {
                        outputManager.sendServerRequestToClient();
                    } catch (MyException ex) {
                        log4j2.error(ex.getMessage());
                    }
                }

                if ("EOF".equals(e.getMessage())) {
                    log4j2.debug("символ EOF");
                }

                if (threadList.size() > 1) {
                    threadList.remove(0).interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Обновляет историю команд
     * @param clientRequest имя команды
     */
    private void updateHistory(ClientRequest clientRequest){
        if (history.size() >= 14) history.removeLast();
        history.addFirst(clientRequest.getName());
    }
}
