package lab6.commands.Server;

import lab6.excepcions.MyException;
import lab6.items.MusicBand;
import lab6.tools.StopSignal;
import lab6.tools.serverIOManagers.DataBaseManager;
import lab6.tools.serverIOManagers.ServerOutputManager;

import java.util.Set;

public class Update extends Command {
    private Set<MusicBand> musicBands;
    DataBaseManager dataBaseManager;

    public Update(ServerOutputManager outputManager, Set<MusicBand> musicBands, DataBaseManager dataBaseManager) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному",1, true, outputManager);
        this.musicBands = musicBands;
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean execute() throws MyException, StopSignal {
        int id;
        String[] arguments = clientRequest.getArguments();
        try {
            id = Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            throw new MyException("\"" + arguments[0] + "\"", "не является числом типа int");
        }
        MusicBand newMusicBand = clientRequest.getElement();

        dataBaseManager.update(newMusicBand, clientRequest.getUserData(), id);

        for (MusicBand musicBand:musicBands){
            if (musicBand.getId() == id){
                musicBand.setName(newMusicBand.getName());
                musicBand.setCoordinates(newMusicBand.getCoordinates());
                musicBand.setNumberOfParticipants(newMusicBand.getNumberOfParticipants());
                musicBand.setGenre(newMusicBand.getGenre());
                musicBand.setBestAlbum(newMusicBand.getBestAlbum());

                break;
            }
        }

        outputManager.noAnswer();
        return true;
    }
}
