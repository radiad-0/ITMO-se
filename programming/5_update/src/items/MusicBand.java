package items;

import excepcions.InvalidEnterException;
import excepcions.LessZeroException;
import excepcions.NullException;
import excepcions.NullOrEmptyException;

import java.util.*;

/**
 * Класс данных музыкальной группы, реализует сортировку по умолчанию по id
 */
public class MusicBand implements Comparable<MusicBand>{
    /**
     * Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     */
    private final int id;
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
    private final Date creationDate;
    /**
     * Значение поля должно быть больше 0
     */
    private long numberOfParticipants;
    /**
     * Поле не может быть null
     */
    private MusicGenre genre;
    /**
     * Поле не может быть null
     */
    private Album bestAlbum;
    /**
     * Счетчик для создания уникального id
     */
    private static int idPointer = 0;
    /**
     * Список id удаленных элементов для присваивания их новым экземплярам
     */
    private static LinkedList<Integer> bufferIds = new LinkedList<>();

    /**
     * крнструктор
     * @param name
     * @param coordinates
     * @param numberOfParticipants
     * @param genre
     * @param bestAlbum
     * @throws InvalidEnterException
     */
    public MusicBand(String name, Coordinates coordinates, long numberOfParticipants, MusicGenre genre, Album bestAlbum) throws InvalidEnterException {
        if (name == null || name.isEmpty()) throw new NullOrEmptyException("name");
        if (coordinates == null) throw new NullException("coordinates");
        if (numberOfParticipants <= 0) throw new LessZeroException("numberOfParticipants");
        if (genre == null) throw new NullException("genre");
        if (bestAlbum == null) throw new NullException("bestAlbum");

        if (bufferIds.size() == 0) id = ++MusicBand.idPointer;
        else id = bufferIds.removeFirst();
        this.name = name;
        this.coordinates = coordinates;
        creationDate = new Date();
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    /**
     * Метод сравнения объектов
     * @param musicBand
     * @return
     */
    @Override
    public int compareTo(MusicBand musicBand) {
        return this.getId() - musicBand.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getXCoordinate() {
        return coordinates.getX();
    }

    public double getYCoordinate() {
        return coordinates.getY();
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    public String getBestAlbumName() {
        return bestAlbum.getName();
    }

    public int getBestAlbumLength() {
        return bestAlbum.getLength();
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    public static void addToBufferIds(int id){
        bufferIds.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand musicBand = (MusicBand) o;
        return this.id == musicBand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, genre);
    }

    @Override
    public String toString() {
        return "MusicBand{" +
                "\n\tid=" + id +
                "\n\tname='" + name + '\'' +
                "\n\tcoordinates=" + coordinates +
                "\n\tcreationDate=" + creationDate +
                "\n\tnumberOfParticipants=" + numberOfParticipants +
                "\n\tgenre=" + genre +
                "\n\tbestAlbum=" + bestAlbum +
                "\n}";
    }
}
