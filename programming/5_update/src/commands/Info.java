package commands;

import items.MusicBand;
import basic.OutputManager;

import java.util.Date;
import java.util.HashSet;

public class Info extends Command {
    private OutputManager outputManager;
    private HashSet<MusicBand> musicBands;
    private Date date;

    public Info(OutputManager outputManager, HashSet<MusicBand> musicBands, Date date) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0, false);
        this.outputManager = outputManager;
        this.musicBands = musicBands;
        this.date = date;
    }

    @Override
    public void execute() {
        outputManager.print_LN_ManualModeSimpleMessage("тип: " + musicBands.getClass() + "\n" +
                "дата инициализации: " + date + "\n" +
                "количество элементов: " + musicBands.size());
    }
}
