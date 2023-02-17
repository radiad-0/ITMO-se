package commands;

import excepcions.ElementWithIdNotFoundException;
import excepcions.InvalidEnterException;
import items.MusicBand;

import java.util.HashSet;

public class RemoveById extends Command {
    public final String name = "remove_by_id";
    private HashSet<MusicBand> musicBands;

    public RemoveById(HashSet<MusicBand> musicBands) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id", 1, false);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws InvalidEnterException {
        int id;
        String[] arguments = commandParameter.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new InvalidEnterException("\"" + arguments[0] + "\"", "не является числом типа int");
        }

        if (musicBands.removeIf(musicBand -> musicBand.getId() == id)) MusicBand.addToBufferIds(id);
        else throw new ElementWithIdNotFoundException(id);

    }
}
