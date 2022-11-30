package commands;

import items.MusicBand;
import basic.OutputManager;

import java.util.HashSet;

public class Show extends Command {
    private OutputManager outputManager;
    private HashSet<MusicBand> musicBands;


    public Show(OutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", 0, false);
        this.outputManager = outputManager;
        this.musicBands = musicBands;
    }

    @Override
    public void execute() {
        musicBands.forEach(outputManager::print_LN_ManualModeSimpleMessage);
    }
}
