package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.ArrayList;
import java.util.Collection;

public class Help extends Command {
    private ArrayList<Command> commands;

    public Help(ServerOutputManager outputManager, Collection<Command> commands) {
        super("help", "вывести справку по доступным командам", 0, false, outputManager);
        this.commands = new ArrayList<>(commands);
    }

    @Override
    public boolean execute() throws MyException {
        commands.forEach(command -> outputManager.writelnToBuffer(command.getDescription()));
        outputManager.sendServerRequestToClient();
        return false;
    }

    public void setCommands(Collection<Command> commands) {
        this.commands = new ArrayList<>(commands);
    }
}
