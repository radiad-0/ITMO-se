package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.Set;

public class Show extends Command {
    private Set<MusicBand> musicBands;


    public Show(ServerOutputManager outputManager, Set<MusicBand> musicBands) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public boolean execute() throws MyException {
        musicBands.stream().sorted().forEach(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
        return false;
    }
}
