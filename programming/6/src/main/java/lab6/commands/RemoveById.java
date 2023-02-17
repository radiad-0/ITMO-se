package lab6.commands;

import lab6.excepcions.ElementWithIdNotFoundException;
import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class RemoveById extends Command {
    public final String name = "remove_by_id";
    private HashSet<MusicBand> musicBands;

    public RemoveById(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id", 1, false, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException {
        int id;
        String[] arguments = clientRequest.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new MyException("\"" + arguments[0] + "\"", "не является числом типа int");
        }

        if (musicBands.removeIf(musicBand -> musicBand.getId() == id)) MusicBand.addToBufferIds(id);
        else throw new ElementWithIdNotFoundException(id);
        outputManager.noAnswer();
    }
}
