package commands;

import basic.StopSignal;

public class Exit extends Command {

    public Exit() {
        super("exit", "exit : завершить работу клиентского приложения", 0, false);
    }

    @Override
    public void execute() throws StopSignal {
        throw new StopSignal();
    }
}
