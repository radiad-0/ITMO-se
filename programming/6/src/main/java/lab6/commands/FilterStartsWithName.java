package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.HashSet;
import java.util.stream.Collectors;

public class FilterStartsWithName extends Command {
    private HashSet<MusicBand> musicBands;

    public FilterStartsWithName(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("filter_starts_with_name", "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки", 1, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        String argument = clientRequest.getArguments()[0];
        musicBands.stream().filter(musicBand -> musicBand.getName().startsWith(argument))
                .collect(Collectors.toList()).forEach(outputManager::writelnToBuffer);
        outputManager.sendServerRequestToClient();
    }
}
