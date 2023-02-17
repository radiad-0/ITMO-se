package commands;

import items.MusicBand;

import java.util.HashSet;

public class Clear extends Command {
    private HashSet<MusicBand> musicBands;

    public Clear(HashSet<MusicBand> musicBands) {
        super("clear", "clear : очистить коллекцию", 0, false);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() {
        musicBands.clear();
    }
}
