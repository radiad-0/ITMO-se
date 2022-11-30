package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.serverIOManagers.DataBaseManager;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.ArrayList;
import java.util.Set;

public class Clear extends Command {
    private Set<MusicBand> musicBands;
    DataBaseManager dataBaseManager;

    public Clear(ServerOutputManager outputManager, Set<MusicBand> musicBands, DataBaseManager dataBaseManager) {
        super("clear", "clear : очистить коллекцию", 0, false, outputManager);
        this.musicBands = musicBands;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean execute() throws MyException {
        ArrayList<Integer> ids = dataBaseManager.clear(clientRequest.getUserData());
        musicBands.removeIf(musicBand -> ids.contains(musicBand.getId()));
        outputManager.noAnswer();
        return false;
    }
}
