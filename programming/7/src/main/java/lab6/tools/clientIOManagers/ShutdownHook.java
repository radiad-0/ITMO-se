package lab6.tools.clientIOManagers;

import lab6.excepcions.MyException;

public class ShutdownHook extends Thread {
    private ClientOutputManager outputManager;

    public ShutdownHook(ClientOutputManager outputManager) {
        this.outputManager = outputManager;
    }

    @Override
    public void run(){
        try {
            outputManager.println("программа завершена");
            outputManager.sendClientRequest(new ClientRequest("exit", "exit"));
        } catch (MyException ignored) {}
    }
}

