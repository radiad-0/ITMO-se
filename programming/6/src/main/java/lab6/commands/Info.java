package lab6.commands;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.Date;
import java.util.HashSet;

public class Info extends Command {
    private HashSet<MusicBand> musicBands;
    private Date date;

    public Info(ServerOutputManager outputManager, HashSet<MusicBand> musicBands, Date date) {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0, false, outputManager);
        this.musicBands = musicBands;
        this.date = date;
    }

    @Override
    public void execute() throws MyException {
        outputManager.printlnToClient("тип: " + musicBands.getClass() + "\n" +
                "дата инициализации: " + date + "\n" +
                "количество элементов: " + musicBands.size());
    }
}
