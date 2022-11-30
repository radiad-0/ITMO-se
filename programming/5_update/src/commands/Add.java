package commands;

import items.MusicBand;
import basic.StopSignal;

import java.util.HashSet;

public class Add extends Command {
    private HashSet<MusicBand> musicBands;

    public Add(HashSet<MusicBand> musicBands) {
        super("add", "add {element} : добавить новый элемент в коллекцию", 0, true);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal {
        musicBands.add(commandParameter.getElement());
    }
}
