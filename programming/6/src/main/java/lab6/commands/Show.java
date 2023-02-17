package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.HashSet;

public class Show extends Command {
    private HashSet<MusicBand> musicBands;


    public Show(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        musicBands.forEach(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
    }
}
