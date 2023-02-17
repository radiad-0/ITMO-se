package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientRequest;

public abstract class Command {
    /**
     * Имя команды
     */
    private final String name;

    /**
     * Описание команды
     */
    private final String description;

    /**
     * Количество аргументов команды
     */
    private final int numberOfArguments;

    /**
     * Параметры команды {@link ClientRequest}
     */
    protected ClientRequest clientRequest;

    public Command(String name, String description, int numberOfArguments) {
        this.name = name;
        this.description = description;
        this.numberOfArguments = numberOfArguments;
    }

    public abstract boolean execute() throws MyException, StopSignal;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }

    public void setParameters(ClientRequest clientRequest){
        this.clientRequest = clientRequest;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
