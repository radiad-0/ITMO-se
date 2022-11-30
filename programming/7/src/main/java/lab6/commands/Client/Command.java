package lab6.commands.Client;

import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;

public abstract class Command extends lab6.commands.Command {
    protected ClientInputManager inputManager;
    protected ClientOutputManager outputManager;

    public Command(String name, String description, int numberOfArguments, ClientInputManager inputManager, ClientOutputManager outputManager) {
        super(name, description, numberOfArguments);
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }
}
