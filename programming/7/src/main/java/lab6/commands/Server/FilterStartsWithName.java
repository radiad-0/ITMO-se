package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.Set;
import java.util.stream.Collectors;

public class FilterStartsWithName extends Command {
    private Set<MusicBand> musicBands;

    public FilterStartsWithName(ServerOutputManager outputManager, Set<MusicBand> musicBands) {
        super("filter_starts_with_name", "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки", 1, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public boolean execute() throws MyException {
        String argument = clientRequest.getArguments()[0];
        musicBands.stream().filter(musicBand -> musicBand.getName().startsWith(argument))
                .collect(Collectors.toList()).forEach(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
        return false;
    }
}
