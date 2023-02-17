package lab6.commands.Server;

import lab6.excepcions.DataBaseException;
import lab6.excepcions.ElementWithIdNotFoundException;
import lab6.excepcions.MyException;
import lab6.excepcions.UserNotFoundException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.DataBaseManager;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.Set;

public class RemoveLower extends Command {
    private Set<MusicBand> musicBands;
    DataBaseManager dataBaseManager;

    public RemoveLower(ServerOutputManager outputManager, Set<MusicBand> musicBands, DataBaseManager dataBaseManager) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", 0, true, outputManager);
        this.musicBands = musicBands;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean execute() throws StopSignal, MyException {
        MusicBand compareMusicBand = clientRequest.getElement();
        musicBands.stream().filter(musicBand -> musicBand.compareTo(compareMusicBand) < 0).forEach(musicBand -> {
            try {
                dataBaseManager.remove(musicBand.getId(), clientRequest.getUserData());
            } catch (DataBaseException | ElementWithIdNotFoundException | UserNotFoundException ignored) {}
        });
        musicBands.removeIf(musicBand -> musicBand.compareTo(compareMusicBand) < 0);
        outputManager.noAnswer();
        return false;
    }
}
