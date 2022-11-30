package lab6.commands.Client;

import lab6.excepcions.MyException;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;

import java.util.ArrayList;
import java.util.Collection;

public class Help extends Command {
    private ArrayList<lab6.commands.Client.Command> commands;

    public Help(ClientInputManager inputManager, ClientOutputManager outputManager, Collection<lab6.commands.Client.Command> commands) {
        super("help", "вывести справку по доступным командам", 0, inputManager, outputManager);
        this.commands = new ArrayList<>(commands);
    }

    @Override
    public boolean execute() throws MyException, StopSignal {
        commands.stream().filter(command -> command != this).forEach(outputManager::println);
        return false;
    }
}
