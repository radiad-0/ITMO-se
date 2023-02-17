package lab6.commands;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class Add extends Command {
    private HashSet<MusicBand> musicBands;

    public Add(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("add", "add {element} : добавить новый элемент в коллекцию", 0, true, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws StopSignal, MyException {
        musicBands.add(clientRequest.getElement());
        outputManager.noAnswer();
    }
}
