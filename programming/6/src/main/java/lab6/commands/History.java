package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.LinkedList;

public class History extends Command {
    private LinkedList<String> history;

    public History(ServerOutputManager outputManager, LinkedList<String> history) {
        super("history", "history : вывести последние 14 команд (без их аргументов)", 0, false, outputManager);
        this.history = history;
    }

    @Override
    public void execute() throws MyException {
        outputManager.writelnToBuffer("последние " + history.size() + " команд:");
        history.forEach(h -> outputManager.writeToBuffer(h + "; "));
        outputManager.writelnToBuffer("");
        outputManager.sendServerRequestToClient();
    }
}
