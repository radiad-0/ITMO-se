package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.Set;

public class MinById extends Command {
    private Set<MusicBand> musicBands;

    public MinById(ServerOutputManager outputManager, Set<MusicBand> musicBands) {
        super("min_by_id", "min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным", 0, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public boolean execute() throws MyException {
        musicBands.stream().min(MusicBand::compareTo).ifPresent(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
        return false;
    }
}
