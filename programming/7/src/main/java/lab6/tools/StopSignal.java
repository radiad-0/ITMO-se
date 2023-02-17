package lab6.tools;

/**
 * Сигнал, завершающий клиентское приложение или скрипт
 */
public class StopSignal extends Throwable{
    public StopSignal(){
        super();
    }

    public StopSignal(String signal){
        super(signal);
    }
}
