package basic;

import excepcions.FileNotAccessException;
import excepcions.InvalidEnterException;
import excepcions.NullException;
import items.Album;
import items.Coordinates;
import items.MusicBand;
import items.MusicGenre;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class XmlManager extends FileManager {
    private HashSet<MusicBand> musicBands;

    public XmlManager(String FILENAME, HashSet<MusicBand> musicBands) {
        super(FILENAME);
        this.musicBands = musicBands;
    }

    @Override
    public void parseMusicBands() throws ParserConfigurationException, IOException, SAXException, InvalidEnterException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(getFileName()));

        NodeList musicBandElements = document.getDocumentElement().getElementsByTagName("musicBand");

        for (int i = 0; i < musicBandElements.getLength(); i++) {
            Node musicBand = musicBandElements.item(i);
            NamedNodeMap musicBandAttributes = musicBand.getAttributes();

            if (((Element) musicBand).getElementsByTagName("coordinates").getLength() != 1 || ((Element) musicBand).getElementsByTagName("bestAlbum").getLength() != 1){
                throw new InvalidEnterException("входные данные", "неверное количество составных полей в элементе " + (1+i));
            }

            NamedNodeMap coordinatesAttributes = ((Element) musicBand).getElementsByTagName("coordinates").item(0).getAttributes();
            NamedNodeMap bestAlbumAttributes = ((Element) musicBand).getElementsByTagName("bestAlbum").item(0).getAttributes();

            if (musicBandAttributes.getLength() != 3 || coordinatesAttributes.getLength() != 2 || bestAlbumAttributes.getLength() != 2){
                throw new InvalidEnterException("входные данные", "неверное количество полей в элементе " + (1+i));
            }


            String name = musicBandAttributes.getNamedItem("name").getNodeValue();
            int x = Integer.parseInt(coordinatesAttributes.getNamedItem("x").getNodeValue());
            double y = Double.parseDouble(coordinatesAttributes.getNamedItem("y").getNodeValue());
            Coordinates coordinates = new Coordinates(x, y);
            long numberOfParticipants = Long.parseLong(musicBandAttributes.getNamedItem("numberOfParticipants").getNodeValue());
            MusicGenre genre = MusicGenre.valueOf(musicBandAttributes.getNamedItem("genre").getNodeValue());
            String nameOfBestAlbum = bestAlbumAttributes.getNamedItem("name").getNodeValue();
            int lengthOfBestAlbum = Integer.parseInt(bestAlbumAttributes.getNamedItem("length").getNodeValue());
            Album bestAlbum = new Album(nameOfBestAlbum, lengthOfBestAlbum);

            musicBands.add(new MusicBand(name, coordinates, numberOfParticipants, genre, bestAlbum));
        }
    }

    @Override
    public void saveMusicBands() throws FileNotAccessException, NullException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFileName()));

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<musicBands>\n");

            for (MusicBand musicBand : musicBands) {
                writer.write("\t<musicBand name=\"" + musicBand.getName() + "\" numberOfParticipants=\"" + musicBand.getNumberOfParticipants() + "\" genre=\"" + musicBand.getGenre() + "\">\n" +
                        "\t\t<coordinates x=\"" + musicBand.getXCoordinate() + "\" y=\"" + musicBand.getYCoordinate() + "\"/>\n" +
                        "\t\t<bestAlbum name=\"" + musicBand.getBestAlbumName() + "\" length=\"" + musicBand.getBestAlbumLength() + "\"/>\n" +
                        "\t</musicBand>\n");
            }
            writer.write("</musicBands>");

            writer.close();
        } catch (IOException e) {
            throw new FileNotAccessException();
        } catch (NullPointerException e) {
            throw new NullException("файл на сохранение");
        }
    }
}
