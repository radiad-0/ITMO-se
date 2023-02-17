package commands;

import excepcions.ElementWithIdNotFoundException;
import excepcions.InvalidEnterException;
import items.MusicBand;
import basic.StopSignal;

import java.util.HashSet;

public class Update extends Command {
    public final String name = "update";
    private HashSet<MusicBand> musicBands;

    public Update(HashSet<MusicBand> musicBands) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному", 1, true);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws InvalidEnterException, StopSignal {
        int id;
        String[] arguments = commandParameter.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new InvalidEnterException("\"" + arguments[0] + "\"", "не является числом типа int");
        }
        boolean updated = false;
        for (MusicBand musicBand:musicBands){
            if (musicBand.getId() == id){
                MusicBand newMusicBand = commandParameter.getElement();

                musicBand.setName(newMusicBand.getName());
                musicBand.setCoordinates(newMusicBand.getCoordinates());
                musicBand.setNumberOfParticipants(newMusicBand.getNumberOfParticipants());
                musicBand.setGenre(newMusicBand.getGenre());
                musicBand.setBestAlbum(newMusicBand.getBestAlbum());

                updated = true;
                break;
            }
        }
        if (!updated) throw new ElementWithIdNotFoundException(id);
    }
}
