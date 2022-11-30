package lab6.tools.serverIOManagers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShutdownHook extends Thread {
    private static final Logger log4j2 = LogManager.getLogger();
    private ServerOutputManager outputManager;

    public ShutdownHook(ServerOutputManager outputManager) {
        this.outputManager = outputManager;
    }

    @Override
    public void run(){
        log4j2.info("_________Сервер___завершил___работу_________");
    }
}
