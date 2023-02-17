package lab6.tools.serverIOManagers;

import lab6.excepcions.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShutdownHook extends Thread {
    private static final Logger log4j2 = LogManager.getLogger();
    private FileManager fileManager;

    public ShutdownHook(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void run(){
        log4j2.info("_________Сервер___завершил___работу_________");
        log4j2.debug("сохранение коллекции");
        try {
            fileManager.saveMusicBands();
        } catch (MyException ex) {
            log4j2.warn(ex.getMessage());
        }
    }
}
