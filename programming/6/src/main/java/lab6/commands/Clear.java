package lab6.commands;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class Clear extends Command {
    private HashSet<MusicBand> musicBands;

    public Clear(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("clear", "clear : очистить коллекцию", 0, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        MusicBand.clearCollectionsWithMusicBands(musicBands);
        outputManager.noAnswer();
    }
}
