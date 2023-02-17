package commands;

import basic.Program;
import basic.OutputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private HashSet<File> scriptsFileNames;
    OutputManager outputManager;
    private Program program;

    public ExecuteScript(OutputManager outputManager, Program program) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", 1, false);
        this.outputManager = outputManager;
        this.program = program;
        scriptsFileNames = new HashSet<>();
    }

    @Override
    public void execute() {
        try {
            String argument = commandParameter.getArguments()[0];
            File file = new File(argument);
            if (scriptsFileNames.contains(file)){
                outputManager.print_LN_ScriptModeError("команда {execute_script " + argument
                        + "} принудительно завершена\nПричина: бесконечная рекурсия");
                return;
            }

            program.setScriptMode(new Scanner(file));
            scriptsFileNames.add(file);

            program.run();

            program.setManualMode(new Scanner(System.in));
            scriptsFileNames.remove(file);

        } catch (FileNotFoundException e) {
            outputManager.print_LN_ManualModeError(e.getMessage());
        }
    }
}
