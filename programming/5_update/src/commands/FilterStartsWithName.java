package commands;

import excepcions.InvalidArgumentException;
import items.MusicBand;
import basic.OutputManager;

import java.util.HashSet;
import java.util.stream.Collectors;

public class FilterStartsWithName extends Command {
    private OutputManager outputManager;
    private HashSet<MusicBand> musicBands;

    public FilterStartsWithName(OutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("filter_starts_with_name", "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки", 1, false);
        this.outputManager = outputManager;
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws InvalidArgumentException {
        String argument = commandParameter.getArguments()[0];
        musicBands.stream().filter(musicBand -> musicBand.getName().startsWith(argument))
                .collect(Collectors.toList()).forEach(outputManager::print_LN_ManualModeSimpleMessage);

    }
}
