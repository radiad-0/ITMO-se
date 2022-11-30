package lab6.tools.clientIOManagers;

import lab6.tools.*;
import lab6.excepcions.*;
import lab6.items.Album;
import lab6.items.Coordinates;
import lab6.items.MusicBand;
import lab6.items.MusicGenre;

import java.util.*;

/**
 * Класс, читающий пользовательский ввод
 */
public class ClientInputManager {
    private ClientOutputManager outputManager;
    private UDPConnection udpConnection;
    private ServerRequest lastRequest;
    private Scanner scanner;

    public ClientInputManager(ClientOutputManager outputManager, UDPConnection udpConnection, Scanner scanner) {
        this.outputManager = outputManager;
        this.udpConnection = udpConnection;
        this.scanner = scanner;
    }

    /**
     * Читает команду в массив строк, в котором первый элемент - имя команды, остальные - аргументы команды
     * @return объект {@link ClientRequest}
     * @throws StopSignal Конец ввода
     * @throws InvalidCommandException Введена пустая строка
     */
    public ClientRequest getCommandParameters() throws StopSignal, InvalidCommandException {
        String[] commandAndArguments;
        String commandName;

        try {
            outputManager.printManualMode(outputManager.highlightedStyle("Lab6$ "));

            String command = getLine();
            commandAndArguments = command.split("\\s");

            if (commandAndArguments.length == 0) throw new InvalidCommandException(command);
            commandName = commandAndArguments[0];
            outputManager.printLnScriptMode(outputManager.highlightedStyle(commandName + ":"));
        } catch (NoSuchElementException e) {
            throw new StopSignal("EOF");
        }

        String[] arguments = Arrays.copyOfRange(commandAndArguments, 1, commandAndArguments.length);

        return new ClientRequest(commandName, arguments);
    }

    public ServerRequest getServerRequest() throws MyException {
        ServerRequest serverRequest = (ServerRequest) Serializer.deserialize(udpConnection.receiveData());

        if (serverRequest == null || serverRequest.equals(lastRequest)) {
            return getServerRequest();
        }
        lastRequest = serverRequest;
        outputManager.printServerRequest(serverRequest);
        return serverRequest;
    }

    /**
     * Читает ввод пользователя
     * @return строка
     * @throws StopSignal Конец ввода
     */
    public String getLine() throws StopSignal {
        String line;
        try {
            line = scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            throw new StopSignal("EOF");
        }
        return line;
    }

    /**
     * Читает целое число
     * @return число int
     * @throws StopSignal Конец ввода
     */
    public int getInt() throws StopSignal {
        int int_;
        String argument = "";
        while (true) try {
            argument = getLine();
            int_ = Integer.parseInt(argument);
            break;
        } catch (NumberFormatException e) {
            outputManager.printManualMode(outputManager.errorStyle("\"" + argument + "\" не является числом типа int,\nвведите число заново: "));
        }
        return int_;
    }

    /**
     * Читает целое число
     * @return число long
     * @throws StopSignal Конец ввода
     */
    public long getLong() throws StopSignal {
        long long_;
        String argument = "";
        while (true) try {
            argument = getLine();
            long_ = Long.parseLong(argument);
            break;
        }
        catch (NumberFormatException e){
            outputManager.printManualMode(outputManager.errorStyle("\"" + argument + "\" не является числом типа lond,\nвведите число заново: "));
        }
        return long_;
    }

    /**
     * Читает дробное число
     * @return число double
     * @throws StopSignal Конец ввода
     */
    public double getDouble() throws StopSignal {
        double double_;
        String argument = "";
        while (true) try {
            argument = getLine();
            double_ = Double.parseDouble(argument);
            break;
        }
        catch (NumberFormatException e){
            outputManager.printManualMode(outputManager.errorStyle("\"" + argument + "\" не является числом типа double,\nвведите число заново: "));
        }
        return double_;
    }

    /**
     * @return объект {@link MusicGenre}
     * @throws StopSignal Конец ввода
     */
    public MusicGenre getMusicGenre() throws StopSignal {
        MusicGenre musicGenre = null;
        String argument = "";
        outputManager.printManualMode(outputManager.highlightedStyle("выберете один из жанров - " +
                Arrays.toString(MusicGenre.values()) + "\ngenre: "));
        while (true) try {
            argument = getLine();
            musicGenre = MusicGenre.valueOf(argument);
            break;
        } catch (IllegalArgumentException e) {
            outputManager.printManualMode(outputManager.errorStyle("\"" + argument + "\" не является жанром,\nвведите жанр заново: "));
        }
        return musicGenre;
    }

    /**
     * @return объект {@link Coordinates}
     * @throws StopSignal Конец ввода
     */
    public Coordinates getCoordinates() throws StopSignal {
        outputManager.printManualMode(outputManager.highlightedStyle("coordinates:\n\tint x: "));
        int x = getInt();

        outputManager.printManualMode(outputManager.highlightedStyle("\tdouble y: "));
        double y = getDouble();

        return new Coordinates(x, y);
    }

    /**
     * @return объект {@link Album}
     * @throws StopSignal Конец ввода
     */
    public Album getAlbum() throws StopSignal {
        while (true) try {
            outputManager.printManualMode(outputManager.highlightedStyle("bestAlbum:\n\tString name(не null, не \"\"): "));
            String bestAlbumName = getLine();

            outputManager.printManualMode(outputManager.highlightedStyle("\tint length(должно быть > 0): "));
            int length = getInt();

            return new Album(bestAlbumName, length);
        } catch (MyException e) {
            outputManager.printLnManualMode(outputManager.errorStyle(e.getMessage()));
            outputManager.printLnManualMode(outputManager.highlightedStyle("попытайтесь еще раз сначала, читайте подсказки в скобках"));
        }
    }

    /**
     * Читает элемент коллекции
     * @return объект {@link MusicBand}
     * @throws StopSignal Конец ввода
     */
    public MusicBand getMusicBand() throws StopSignal {
        while (true) try {
            outputManager.printManualMode(outputManager.highlightedStyle("String name(не null, не \"\"): "));
            String name = getLine();

            Coordinates coordinates = getCoordinates();

            outputManager.printManualMode(outputManager.highlightedStyle("long numberOfParticipants(должно быть > 0): "));
            long numberOfParticipants = getLong();

            MusicGenre genre = getMusicGenre();

            Album bestAlbum = getAlbum();

            return new MusicBand(name, coordinates, numberOfParticipants, genre, bestAlbum);
        } catch (MyException e) {
            outputManager.printLnManualMode(outputManager.errorStyle(e.getMessage()));
            outputManager.printLnManualMode(outputManager.highlightedStyle("попытайтесь еще раз сначала, читайте подсказки в скобках"));
        }
    }

    public void setManualMode(Scanner scanner){
        this.scanner = scanner;
        outputManager.setManualMode();
    }

    public void setScriptMode(Scanner scanner){
        this.scanner = scanner;
        outputManager.setScriptMode();
    }

    public void closeScanner(){
        scanner.close();
    }
}
