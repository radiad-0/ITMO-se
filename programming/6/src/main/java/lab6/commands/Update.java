package lab6.commands;

import lab6.excepcions.ElementWithIdNotFoundException;
import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.HashSet;

public class Update extends Command {
    public final String name = "update";
    private HashSet<MusicBand> musicBands;

    public Update(ServerOutputManager outputManager, HashSet<MusicBand> musicBands) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному",1, true, outputManager);
        this.musicBands = musicBands;
    }

    @Override
    public void execute() throws MyException, StopSignal {
        int id;
        String[] arguments = clientRequest.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new MyException("\"" + arguments[0] + "\"", "не является числом типа int");
        }
        boolean updated = false;
        MusicBand newMusicBand = clientRequest.getElement();
        for (MusicBand musicBand:musicBands){
            if (musicBand.getId() == id){
                musicBand.setName(newMusicBand.getName());
                musicBand.setCoordinates(newMusicBand.getCoordinates());
                musicBand.setNumberOfParticipants(newMusicBand.getNumberOfParticipants());
                musicBand.setGenre(newMusicBand.getGenre());
                musicBand.setBestAlbum(newMusicBand.getBestAlbum());

                updated = true;
                break;
            }
        }
        MusicBand.addToBufferIds(newMusicBand.getId());
        if (!updated) throw new ElementWithIdNotFoundException(id);
        outputManager.noAnswer();
    }
}
