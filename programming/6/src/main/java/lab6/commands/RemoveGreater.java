package lab6.commands;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class RemoveGreater extends Command {
    private HashSet<MusicBand> musicBands;

    public RemoveGreater(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный", 0, true, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal, MyException {
        MusicBand compareMusicBand = clientRequest.getElement();
        musicBands.removeIf(musicBand -> musicBand.compareTo(compareMusicBand) > 0);
        MusicBand.addToBufferIds(compareMusicBand.getId());
        outputManager.noAnswer();
    }
}
