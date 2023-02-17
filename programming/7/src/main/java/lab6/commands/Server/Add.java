package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.DataBaseManager;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.Set;

public class Add extends Command {
    private Set<MusicBand> musicBands;
    private DataBaseManager dataBaseManager;

    public Add(ServerOutputManager outputManager, Set<MusicBand> musicBands, DataBaseManager dataBaseManager) {
        super("add", "add {element} : добавить новый элемент в коллекцию", 0, true, outputManager);
        this.musicBands = musicBands;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean execute() throws StopSignal, MyException {
        MusicBand newMusicBand = clientRequest.getElement();
        dataBaseManager.addElement(newMusicBand, clientRequest.getUserData());
        musicBands.add(newMusicBand);
        outputManager.noAnswer();
        return false;
    }
}
