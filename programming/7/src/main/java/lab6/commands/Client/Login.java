package lab6.commands.Client;

import lab6.excepcions.MyException;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;
import lab6.tools.clientIOManagers.ClientRequest;

public class Login extends Command {

    public Login(ClientInputManager inputManager, ClientOutputManager outputManager) {
        super("login", "login : войти в существующий аккаунт", 0, inputManager, outputManager);
    }

    @Override
    public boolean execute() throws MyException, StopSignal {
        outputManager.printLnManualMode(outputManager.highlightedStyle("Авторизация:"));
        ClientRequest.setDefaultUserData(inputManager.getUserData());
        return true;
    }
}
