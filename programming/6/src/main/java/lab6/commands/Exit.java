package lab6.commands;

import lab6.excepcions.InvalidArgumentException;
import lab6.tools.StopSignal;

import java.util.Arrays;
import java.util.HashSet;

public class Exit extends Command {
    private final HashSet<String> availableSignal = new HashSet<>(Arrays.asList(null, "endOfScript", "EOF", "exit"));

    public Exit() {
        super("exit", "exit : завершить работу клиентского приложения", -1, false, null);
    }

    @Override
    public void execute() throws StopSignal, InvalidArgumentException {
        String signal = null;
        if (clientRequest.getArguments().length > 1) throw new InvalidArgumentException();
        if (clientRequest.getArguments().length > 0) signal = clientRequest.getArguments()[0];
        if (!availableSignal.contains(signal)) throw new InvalidArgumentException();
        throw new StopSignal(signal);
    }
}
