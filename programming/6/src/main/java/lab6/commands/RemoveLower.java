package lab6.commands;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class RemoveLower extends Command {
    private HashSet<MusicBand> musicBands;

    public RemoveLower(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", 0, true, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal, MyException {
        MusicBand compareMusicBand = clientRequest.getElement();
        musicBands.removeIf(musicBand -> musicBand.compareTo(compareMusicBand) < 0);
        MusicBand.addToBufferIds(compareMusicBand.getId());
        outputManager.noAnswer();
    }
}
