package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.serverIOManagers.DataBaseManager;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.Set;

public class RemoveById extends Command {
    private Set<MusicBand> musicBands;
    DataBaseManager dataBaseManager;

    public RemoveById(ServerOutputManager outputManager, Set<MusicBand> musicBands, DataBaseManager dataBaseManager) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id", 1, false, outputManager);
        this.musicBands = musicBands;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean execute() throws MyException {
        int id;
        String[] arguments = clientRequest.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new MyException("\"" + arguments[0] + "\"", "не является числом типа int");
        }

        dataBaseManager.remove(id, clientRequest.getUserData());
        musicBands.removeIf(musicBand -> musicBand.getId() == id);
        outputManager.noAnswer();
        return false;
    }
}
