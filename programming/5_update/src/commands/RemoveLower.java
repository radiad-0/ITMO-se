package commands;

import items.MusicBand;
import basic.StopSignal;

import java.util.HashSet;

public class RemoveLower extends Command {
    private HashSet<MusicBand> musicBands;

    public RemoveLower(HashSet<MusicBand> musicBands) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", 0, true);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal {
        MusicBand compareMusicBand = commandParameter.getElement();
        musicBands.removeIf(musicBand -> musicBand.compareTo(compareMusicBand) < 0);
    }
}
