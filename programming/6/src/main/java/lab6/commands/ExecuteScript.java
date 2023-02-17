package lab6.commands;

import lab6.excepcions.MyException;
import lab6.excepcions.RecursiveCommandExecutionException;
import lab6.tools.serverIOManagers.ServerOutputManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.LinkedList;

public class ExecuteScript extends Command {
    private static final Logger log4j2 = LogManager.getLogger();
    private LinkedList<File> scriptsFileNames;
    private boolean inRun;

    public ExecuteScript(ServerOutputManager outputManager) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", 1, false, outputManager);
        scriptsFileNames = new LinkedList<>();
        inRun = false;
    }

    @Override
    public void execute() throws MyException {
        String argument = clientRequest.getArguments()[0];
        File file = new File(argument);
        inRun = true;
        if (scriptsFileNames.contains(file)){
            inRun = false;
            scriptsFileNames.clear();
            outputManager.setManualMode();
            throw new RecursiveCommandExecutionException("{execute_script " + argument);
        }

        scriptsFileNames.addLast(file);
        outputManager.setScriptMode(file);
        outputManager.sendServerRequestToClient();
    }

    public void endOfScriptFile(){
        scriptsFileNames.removeLast();
        if (scriptsFileNames.isEmpty()) {
            inRun = false;
        }
    }
}
