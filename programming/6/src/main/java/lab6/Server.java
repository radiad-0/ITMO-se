package lab6;

import lab6.excepcions.*;
import lab6.tools.*;
import lab6.tools.serverIOManagers.*;
import lab6.commands.*;
import lab6.items.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import lab6.tools.ClientRequest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;


public class Server {
    /**
     * коллекция
     */
    private HashSet<MusicBand> musicBands;

    /**
     * дата инициализации коллекции
     */
    private Date date;

    /**
     * список последних 14-ти команд
     */
    private LinkedList<String> history;

    /**
     * словарь доступных команд
     */
    private HashMap<String, Command> commands;


    private UDPConnection udpConnection;
    private ServerInputManager inputManager;
    private ServerOutputManager outputManager;

    /**
     * управление файлом с коллекцией
     */
    private FileManager fileManager;
    private static final int port = 3468;
    private static final Logger log4j2 = LogManager.getLogger();
    private boolean inRun;

    /**
     * Читает из переменной окружения имя файла, в котором храниться коллекция.
     * Запускает сервер
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String fileName = System.getenv("FILENAME");

        try {
            Server lab6 = new Server(fileName);
            lab6.run();
        } catch (MyException e) {
            log4j2.error(e.getMessage());
        }
    }

    /**
     * @param fileName имя файла, в котором храниться коллекция
     * @throws InvalidConnectException Ошибка соединения
     */
    public Server(String fileName) throws InvalidConnectException {
        musicBands = new HashSet<>();
        date = new Date();
        history = new LinkedList<>();

        udpConnection = new UDPConnection(port);
        outputManager = new ServerOutputManager(udpConnection);
        udpConnection.setOutputManager(outputManager);
        udpConnection.bindAddress();

        commands = new HashMap<>();

        commands.put("info", new Info(outputManager, musicBands, date));
        commands.put("show", new Show(outputManager, musicBands));
        commands.put("add", new Add(outputManager, musicBands));
        commands.put("update", new Update(outputManager, musicBands));
        commands.put("remove_by_id", new RemoveById(outputManager, musicBands));
        commands.put("clear", new Clear(outputManager, musicBands));
        commands.put("execute_script", new ExecuteScript(outputManager));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(outputManager, musicBands));
        commands.put("remove_lower", new RemoveLower(outputManager, musicBands));
        commands.put("history", new History(outputManager, history));
        commands.put("min_by_id", new MinById(outputManager, musicBands));
        commands.put("count_less_than_genre", new CountLessThanGenre(outputManager, musicBands));
        commands.put("filter_starts_with_name", new FilterStartsWithName(outputManager, musicBands));
        commands.put("help", new Help(outputManager, commands.values()));

        inputManager = new ServerInputManager(outputManager, udpConnection);
        fileManager = new XmlManager(fileName, musicBands);

        try {
            fileManager.parseMusicBands();
        } catch (ParserConfigurationException | IOException | SAXException | MyException e) {
            log4j2.warn("проблемы с загрузкой файла:\n" + e.getMessage());
        } catch (NullPointerException e){
            log4j2.warn("проблемы с загрузкой файла: переменная окружения не задана");
        }

        inRun = true;
        log4j2.info("_________Сервер___начал___работу_________");
    }

    /**
     * Принимает, обрабатывает и исполняет команды
     * @throws MyException ошибка сериализация {@link Serializer#serialize(Object)} или соединения {@link UDPConnection#sendData(byte[])}
     */
    public void run() throws MyException {

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(fileManager));

        ConsoleThread consoleThread = new ConsoleThread(fileManager);
        consoleThread.start();

        ClientRequest clientRequest;
        Command command;

        while (inRun) {
            try {
                clientRequest = inputManager.getClientRequest(commands);
                command = commands.get(clientRequest.getName());

                if (command.isNeedElementAsArgument() && clientRequest.getElement() == null) {
                    inputManager.takeElement();
                    continue;
                }

                updateHistory(clientRequest.getName());

                command.setParameters(clientRequest);
                command.execute();
            } catch (MyException e) {
                outputManager.printlnErrorToClient(e.getMessage());
                log4j2.debug(e.getMessage());
            } catch (StopSignal e){
                if ("endOfScript".equals(e.getMessage())){
                    ExecuteScript executeScript = (ExecuteScript) commands.get("execute_script");
                    executeScript.endOfScriptFile();
                    continue;
                }


                log4j2.info("__________Клиент___завершил___работу__________");
                history.clear();

                if (e.getMessage() == null){
                    outputManager.setStopSignal();
                    outputManager.sendServerRequestToClient();
                }

                if ("EOF".equals(e.getMessage())){
                    log4j2.debug("символ EOF");
                }

                log4j2.debug("сохранение коллекции");
                try {
                    fileManager.saveMusicBands();
                } catch (MyException ex) {
                    log4j2.warn(ex.getMessage());
                }
            }

        }
    }

    /**
     * Обновляет историю команд
     * @param command имя команды
     */
    private void updateHistory(String command){
        if (history.size() >= 14) history.removeLast();
        history.addFirst(command);
    }
}
