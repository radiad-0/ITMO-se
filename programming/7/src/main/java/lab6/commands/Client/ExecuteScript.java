package lab6.commands.Client;

import lab6.excepcions.InvalidArgumentException;
import lab6.excepcions.MyException;
import lab6.excepcions.RecursiveCommandExecutionException;
import lab6.tools.StopSignal;
import lab6.tools.clientIOManagers.ClientInputManager;
import lab6.tools.clientIOManagers.ClientOutputManager;

import java.io.File;
import java.util.LinkedList;

public class ExecuteScript extends Command {
    private LinkedList<File> scriptsFiles;

    public ExecuteScript(ClientInputManager inputManager, ClientOutputManager outputManager, LinkedList<File> scriptsFiles) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", 1, inputManager, outputManager);
        this.scriptsFiles = scriptsFiles;
    }

    @Override
    public boolean execute() throws MyException, StopSignal {
        String[] arguments = clientRequest.getArguments();
        if (arguments.length != 1) throw new InvalidArgumentException();
        File scriptFile = new File(arguments[0]);
        if (scriptsFiles.contains(scriptFile)) {
            scriptsFiles.clear();
            inputManager.setManualMode();
            throw new RecursiveCommandExecutionException("execute_script " + scriptFile);
        }
        scriptsFiles.addLast(scriptFile);
        inputManager.setScriptMode();
        return true;
    }
}
