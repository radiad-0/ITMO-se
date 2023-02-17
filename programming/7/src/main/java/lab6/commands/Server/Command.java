package lab6.commands.Server;

import lab6.tools.serverIOManagers.ServerOutputManager;

public abstract class Command extends lab6.commands.Command {
    /**
     * Нужен ли команде элемент {@link lab6.items.MusicBand} в качестве аргумента
     */
    private final boolean needElementAsArgument;

    protected ServerOutputManager outputManager;

    public Command(String name, String description, int numberOfArguments, boolean needElementAsArgument, ServerOutputManager outputManager) {
        super(name, description, numberOfArguments);
        this.needElementAsArgument = needElementAsArgument;
        this.outputManager = outputManager;
    }

    public boolean isNeedElementAsArgument() {
        return needElementAsArgument;
    }
}
