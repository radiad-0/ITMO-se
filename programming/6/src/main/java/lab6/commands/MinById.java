package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.HashSet;

public class MinById extends Command {
    private HashSet<MusicBand> musicBands;

    public MinById(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("min_by_id", "min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным", 0, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        musicBands.stream().min(MusicBand::compareTo).ifPresent(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
    }
}
