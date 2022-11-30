package lab6.tools.serverIOManagers;

import lab6.excepcions.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ConsoleThread extends Thread {
    private static final Logger log4j2 = LogManager.getLogger();
    private FileManager fileManager;

    public ConsoleThread(FileManager fileManager) {
        super("ServerConsole");
        this.fileManager = fileManager;
    }

    @Override
    public void run() {
        String nextLine;
        Scanner scanner = new Scanner(System.in);

        while (true){
            if (scanner.hasNextLine()) {
                nextLine = scanner.nextLine();
                log4j2.debug("в консоль написали: " + nextLine);
                if ("save".equals(nextLine)){
                    log4j2.info("сохранение коллекции");
                    try {
                        fileManager.saveMusicBands();
                    } catch (MyException ex) {
                        log4j2.warn(ex.getMessage());
                    }
                }
            }
        }
    }
}
