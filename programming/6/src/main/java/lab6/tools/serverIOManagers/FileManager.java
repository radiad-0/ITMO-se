package lab6.tools.serverIOManagers;

import lab6.excepcions.FileNotAccessException;
import lab6.excepcions.MyException;
import lab6.excepcions.NullException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Класс для чтения и записи коллекции {@link lab6.items.MusicBand} в файл
 */
public abstract class FileManager {
    private String FileName;

    protected FileManager(String filename) {
        FileName = filename;
    }

    public abstract void parseMusicBands() throws ParserConfigurationException, IOException, SAXException, MyException;
    public abstract void saveMusicBands() throws FileNotAccessException, NullException;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
