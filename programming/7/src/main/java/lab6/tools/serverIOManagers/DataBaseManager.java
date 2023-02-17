package lab6.tools.serverIOManagers;

import lab6.excepcions.*;
import lab6.items.Album;
import lab6.items.Coordinates;
import lab6.items.MusicBand;
import lab6.items.MusicGenre;
import lab6.tools.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DataBaseManager {
    private Connection connection;
    private Statement statement;
    private static final Logger log4j2 = LogManager.getLogger();

    public DataBaseManager(String url, String userName, String password) throws DataBaseException {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(true);
            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "login TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL" +
                    ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS musicbands (" +
                    "id SERIAL PRIMARY KEY CHECK (id > 0)," +
                    "name TEXT NOT NULL CHECK (name <> '')," +
                    "coordinates_x INT," +
                    "coordinates_y REAL," +
                    "creation_date DATE NOT NULL," +
                    "numberOfParticipants BIGINT CHECK(numberOfParticipants > 0)," +
                    "genre TEXT NOT NULL CHECK(genre IN ('ROCK', 'JAZZ', 'BRIT_POP'))," +
                    "bestAlbum_name VARCHAR NOT NULL  CHECK (bestAlbum_name <> '')," +
                    "bestAlbum_length INT CHECK(bestAlbum_length > 0)," +
                    "user_login TEXT NOT NULL REFERENCES USERS(login)" +
                    ");");
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized void addUser(UserData userData) throws DataBaseException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?);")) {
            preparedStatement.setString(1, userData.getLogin());
            preparedStatement.setString(2, userData.getHashPassword());
            if (preparedStatement.executeUpdate() == 0) throw new DataBaseException("пользователь не добавлен");
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized boolean containsUser(UserData userData) throws DataBaseException, InvalidAuthorizationException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login=?;")) {
            preparedStatement.setString(1, userData.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized boolean checkUserPassword(UserData userData) throws InvalidAuthorizationException, DataBaseException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login=?;")) {
            preparedStatement.setString(1, userData.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString("password") != null && resultSet.getString("password").equals(userData.getHashPassword());
            else throw new UserNotFoundException();
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized void addElement(MusicBand musicBand, UserData userData) throws DataBaseException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO musicbands (id, name, coordinates_x, coordinates_y, creation_date, numberOfParticipants, genre, bestAlbum_name, bestAlbum_length, user_login) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, musicBand.getName());
            preparedStatement.setInt(2, musicBand.getXCoordinate());
            preparedStatement.setDouble(3, musicBand.getYCoordinate());
            preparedStatement.setDate(4, new Date(musicBand.getCreationDate().getTime()));
            preparedStatement.setLong(5, musicBand.getNumberOfParticipants());
            preparedStatement.setString(6, String.valueOf(musicBand.getGenre()));
            preparedStatement.setString(7, musicBand.getBestAlbumName());
            preparedStatement.setInt(8, musicBand.getBestAlbumLength());
            preparedStatement.setString(9, userData.getLogin());

            if (preparedStatement.executeUpdate() == 0) throw new DataBaseException("не выполнилась команда INSERT");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) throw new DataBaseException("нет следующей строки");
            musicBand.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized void update(MusicBand musicBand, UserData userData, int id) throws DataBaseException, ElementWithIdNotFoundException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE musicbands SET name=?, coordinates_x=?, coordinates_y=?, creation_date=?, numberOfParticipants=?, genre=?, bestAlbum_name=?, bestAlbum_length=?, user_login=?, WHERE id=?;")) {

            preparedStatement.setString(1, musicBand.getName());
            preparedStatement.setInt(2, musicBand.getXCoordinate());
            preparedStatement.setDouble(3, musicBand.getYCoordinate());
            preparedStatement.setDate(4, new Date(musicBand.getCreationDate().getTime()));
            preparedStatement.setLong(5, musicBand.getNumberOfParticipants());
            preparedStatement.setString(6, String.valueOf(musicBand.getGenre()));
            preparedStatement.setString(7, musicBand.getBestAlbumName());
            preparedStatement.setInt(8, musicBand.getBestAlbumLength());
            preparedStatement.setString(9, userData.getLogin());

            preparedStatement.setInt(10, id);

            if (preparedStatement.executeUpdate() == 0) throw new DataBaseException(new ElementWithIdNotFoundException(id));
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized ArrayList<Integer> clear(UserData userData) throws DataBaseException {
        ArrayList<Integer> ids = usersElementsId(userData);

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM musicbands WHERE user_login=?;")) {

            preparedStatement.setString(1, userData.getLogin());

            if (preparedStatement.executeUpdate() == 0) throw new DataBaseException("у вас нет доступных для редактирования элементов");
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
        return ids;
    }

    private synchronized ArrayList<Integer> usersElementsId(UserData userData) throws DataBaseException {
        ArrayList<Integer> ids = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM musicbands WHERE user_login=?;")) {

            preparedStatement.setString(1, userData.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) ids.add(resultSet.getInt(1));
            return ids;
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized void remove(int id, UserData userData) throws DataBaseException, ElementWithIdNotFoundException, UserNotFoundException {
        if (!isUsersElement(id, userData)) throw new DataBaseException("элемент с этим id не существует или вы не являетесь его создателем");
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM musicbands WHERE id=?;")) {

            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() == 0) throw new ElementWithIdNotFoundException(id);
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    private synchronized boolean isUsersElement(int id, UserData userData) throws DataBaseException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM musicbands WHERE user_login=? AND id=?;")) {

            preparedStatement.setString(1, userData.getLogin());
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            log4j2.warn(e.getMessage());
            throw new DataBaseException(e);
        }
    }

    public synchronized HashSet<MusicBand> getMusicBandsFromDataBase() throws DataBaseException {
        HashSet<MusicBand> musicBands = new HashSet<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM musicbands;");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int x = resultSet.getInt("coordinates_x");
                double y = resultSet.getDouble("coordinates_y");
                Coordinates coordinates = new Coordinates(x, y);
                Date creationDate = new Date(resultSet.getDate("creation_date").getTime());
                long numberOfParticipants = resultSet.getLong("numberOfParticipants");
                MusicGenre genre = MusicGenre.valueOf(resultSet.getString("genre"));
                String nameOfBestAlbum = resultSet.getString("bestAlbum_name");
                int lengthOfBestAlbum = resultSet.getInt("bestAlbum_length");
                Album bestAlbum = new Album(nameOfBestAlbum, lengthOfBestAlbum);

                musicBands.add(new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, genre, bestAlbum));
            }
        } catch (SQLException | MyException e) {
            throw new DataBaseException(new LoadCollectionException(e));
        }
        return musicBands;
    }
}
