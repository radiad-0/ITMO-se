package basic;

import commands.*;
import excepcions.InvalidArgumentException;
import excepcions.InvalidCommandException;
import excepcions.InvalidEnterException;
import items.MusicBand;
import org.xml.sax.SAXException;
import commands.CommandParameter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Program {
    private HashSet<MusicBand> musicBands;
    private Date date;
    private LinkedList<String> history;
    private InputManager inputManager;
    private OutputManager outputManager;
    private FileManager fileManager;
    private HashMap<String, Command> commands;
    private boolean inRun;

    public Program(String fileName, Scanner scanner) {
        musicBands = new HashSet<>();
        date = new Date();
        history = new LinkedList<>();
        outputManager = new OutputManager();

        commands = new HashMap<>();

        commands.put("info", new Info(outputManager, musicBands, date));
        commands.put("show", new Show(outputManager, musicBands));
        commands.put("add", new Add(musicBands));
        commands.put("update", new Update(musicBands));
        commands.put("remove_by_id", new RemoveById(musicBands));
        commands.put("clear", new Clear(musicBands));
        commands.put("execute_script", new ExecuteScript(outputManager, this));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(musicBands));
        commands.put("remove_lower", new RemoveLower(musicBands));
        commands.put("history", new History(outputManager, history));
        commands.put("min_by_id", new MinById(outputManager, musicBands));
        commands.put("count_less_than_genre", new CountLessThanGenre(outputManager, musicBands));
        commands.put("filter_starts_with_name", new FilterStartsWithName(outputManager, musicBands));
        commands.put("help", new Help(outputManager, commands.values()));

        inputManager = new InputManager(outputManager, scanner);
        fileManager = new XmlManager(fileName, musicBands);

        try {
            fileManager.parseMusicBands();
        } catch (ParserConfigurationException | IOException | SAXException | InvalidEnterException e) {
            outputManager.print_LN_ManualModeError("проблемы с загрузкой файла:\n" + e.getMessage());
        } catch (NullPointerException e){
            outputManager.print_LN_ManualModeError("проблемы с загрузкой файла: переменная окружения не задана");
        }

        inRun = true;
        outputManager.print_LN_ManualModeHighlightedMessage("Программа начала работу!\nПо команде help можно получить список доступных комманд.");
    }

    public void run() {
        
        CommandParameter commandParameter;
        Command command;

        while (inRun) {
            try {
                commandParameter = inputManager.getCommandParameters();

                if (!commands.containsKey(commandParameter.getName())) throw new InvalidCommandException(commandParameter.getName());

                command = commands.get(commandParameter.getName());

                if (commandParameter.getArguments().length != command.getNumberOfArguments()) throw new InvalidArgumentException();

                if (command.isNeedElementAsArgument()) commandParameter.setElement(inputManager.getMusicBand());

                updateHistory(commandParameter.getName());

                command.setParameters(commandParameter);
                command.execute();
            } catch (InvalidEnterException e) {
                outputManager.print_LN_ManualModeError(e.getMessage());
            } catch (StopSignal e){
                outputManager.print_LN_ManualModeHighlightedMessage("программа завершена");
                outputManager.print_LN_ScriptModeHighlightedMessage("script completed");
                if (outputManager.isManualMode()) {
                    inRun = false;
                    inputManager.closeScanner();
                }
                break;
            }


        }
    }

    private void updateHistory(String command){
        if (history.size() >= 14) history.removeLast();
        history.addFirst(command);
    }

    public void setManualMode(Scanner scanner){
        inputManager.setManualMode(scanner);
    }

    public void setScriptMode(Scanner scanner){
        inputManager.setScriptMode(scanner);
    }
}
