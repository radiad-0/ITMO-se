package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import lab6.items.MusicBand;

import java.util.Date;
import java.util.Set;

public class Info extends Command {
    private Set<MusicBand> musicBands;
    private Date date;

    public Info(ServerOutputManager outputManager, Set<MusicBand> musicBands, Date date) {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0, false, outputManager);
        this.musicBands = musicBands;
        this.date = date;
    }

    @Override
    public boolean execute() throws MyException {
        outputManager.printlnToClient("тип: " + musicBands.getClass() + "\n" +
                "дата инициализации: " + date + "\n" +
                "количество элементов: " + musicBands.size());
        return false;
    }
}
