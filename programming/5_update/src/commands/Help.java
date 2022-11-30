package commands;

import basic.OutputManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Help extends Command {
    private OutputManager outputManager;
    private ArrayList<Command> commands;

    public Help(OutputManager outputManager, Collection<Command> commands) {
        super("help", "вывести справку по доступным командам", 0, false);
        this.outputManager = outputManager;
        this.commands = new ArrayList<>(commands);
    }

    @Override
    public void execute() {
        commands.forEach(command -> outputManager.print_LN_ManualModeSimpleMessage(command.getDescription()));
    }

    public void setCommands(Collection<Command> commands) {
        this.commands = new ArrayList<>(commands);
    }
}
