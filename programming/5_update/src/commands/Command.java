package commands;

import excepcions.InvalidEnterException;
import basic.StopSignal;

public abstract class Command {
    private final String name;
    private final String description;
    private final int numberOfArguments;
    private final boolean needElementAsArgument;
    protected CommandParameter commandParameter;


    public Command(String name, String description, int numberOfArguments, boolean needElementAsArgument) {
        this.name = name;
        this.description = description;
        this.numberOfArguments = numberOfArguments;
        this.needElementAsArgument = needElementAsArgument;
    }

    public abstract void execute() throws InvalidEnterException, StopSignal;

    public void setParameters(CommandParameter commandParameter){
        this.commandParameter = commandParameter;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }

    public boolean isNeedElementAsArgument() {
        return needElementAsArgument;
    }

    @Override
    public String toString() {
        return getName();
    }
}
