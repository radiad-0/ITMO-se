package basic;

import excepcions.FileNotAccessException;
import excepcions.InvalidEnterException;
import excepcions.NullException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class FileManager {
    private String FileName;

    protected FileManager(String filename) {
        FileName = filename;
    }

    public abstract void parseMusicBands() throws ParserConfigurationException, IOException, SAXException, InvalidEnterException;
    public abstract void saveMusicBands() throws FileNotAccessException, NullException;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
