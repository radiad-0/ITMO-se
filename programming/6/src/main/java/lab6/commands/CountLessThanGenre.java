package lab6.commands;

import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.items.MusicGenre;

import java.util.HashSet;

public class CountLessThanGenre extends Command {
    private HashSet<MusicBand> musicBands;

    public CountLessThanGenre(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("count_less_than_genre", "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного", 1, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        MusicGenre genre;
        String argument = clientRequest.getArguments()[0];
        try {
            genre = MusicGenre.valueOf(argument);
        } catch (IllegalArgumentException e) {
            throw new MyException("\"" + argument + "\"", "не является жанром");
        }

        long count = musicBands.stream().filter(musicBand -> musicBand.getGenre().compareTo(genre) < 0).count();

        outputManager.printlnToClient("count less than genre \"" + genre + "\": " + count);
    }
}
