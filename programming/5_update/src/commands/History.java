package commands;

import basic.OutputManager;

import java.util.LinkedList;

public class History extends Command {
    private OutputManager outputManager;
    private LinkedList<String> history;

    public History(OutputManager outputManager, LinkedList<String> history) {
        super("history", "history : вывести последние 14 команд (без их аргументов)", 0, false);
        this.outputManager = outputManager;
        this.history = history;
    }

    @Override
    public void execute() {
        outputManager.print_LN_ManualModeSimpleMessage("последние " + history.size() + " команд:");
        history.forEach(h -> outputManager.printManualModeSimpleMessage(h + "; "));
        outputManager.print_LN_ManualModeSimpleMessage("");
    }
}
