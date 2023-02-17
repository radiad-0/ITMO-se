package commands;

import items.MusicBand;
import basic.StopSignal;

import java.util.HashSet;

public class RemoveGreater extends Command {
    private HashSet<MusicBand> musicBands;

    public RemoveGreater(HashSet<MusicBand> musicBands) {
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный", 0, true);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal {
        MusicBand compareMusicBand = commandParameter.getElement();
        musicBands.removeIf(musicBand -> musicBand.compareTo(compareMusicBand) > 0);
    }
}
