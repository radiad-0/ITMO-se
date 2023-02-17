package commands;

import items.MusicBand;

public class CommandParameter {
    private String name;
    private String[] arguments;
    private MusicBand element;

    public CommandParameter(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public CommandParameter(String name, String[] arguments, MusicBand element) {
        this.name = name;
        this.arguments = arguments;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public MusicBand getElement() {
        return element;
    }

    public void setElement(MusicBand element) {
        this.element = element;
    }
}
