package commands;

import items.MusicBand;
import basic.OutputManager;

import java.util.HashSet;

public class MinById extends Command {
    private OutputManager outputManager;
    private HashSet<MusicBand> musicBands;

    public MinById(OutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("min_by_id", "min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным", 0, false);
        this.outputManager = outputManager;
        this.musicBands = musicBands;
    }

    @Override
    public void execute() {
        musicBands.stream().min(MusicBand::compareTo).ifPresent(outputManager::print_LN_ManualModeSimpleMessage);

    }
}
