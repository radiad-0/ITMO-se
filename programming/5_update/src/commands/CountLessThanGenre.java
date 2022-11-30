package commands;

import excepcions.InvalidEnterException;
import items.MusicBand;
import items.MusicGenre;
import basic.OutputManager;

import java.util.HashSet;

public class CountLessThanGenre extends Command {
    private OutputManager outputManager;
    private HashSet<MusicBand> musicBands;

    public CountLessThanGenre(OutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("count_less_than_genre", "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного", 1, false);
        this.outputManager = outputManager;
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws InvalidEnterException {
        MusicGenre genre;
        String argument = commandParameter.getArguments()[0];
        try {
            genre = MusicGenre.valueOf(argument);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnterException("\"" + argument + "\"", "не является жанром");
        }

        long count = musicBands.stream().filter(musicBand -> musicBand.getGenre().compareTo(genre) < 0).count();

        outputManager.print_LN_ManualModeSimpleMessage("count less than genre \"" + genre + "\": " + count);
    }
}
