package lab6.commands.Client;

import lab6.excepcions.MyException;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;
import lab6.tools.clientIOManagers.ClientRequest;

public class Register extends Command {

    public Register(ClientInputManager inputManager, ClientOutputManager outputManager) {
        super("register", "register : создать новый аккаунт", 0, inputManager, outputManager);
    }

    @Override
    public boolean execute() throws MyException, StopSignal {
        outputManager.printLnManualMode(outputManager.highlightedStyle("Регистрация:"));
        ClientRequest.setDefaultUserData(inputManager.getUserData());
        ClientRequest.setAuthorizationMode(ClientRequest.AuthorizationMode.REGISTER);
        return true;
    }
}
