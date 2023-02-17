package lab6.tools.serverIOManagers;

import lab6.commands.Server.Command;

import java.io.Serializable;
import java.util.Objects;

public class ServerRequest implements Serializable {
    /**
     * Сообщение, которое будет выведено в консоль клиента
     */
    private String message;
    /**
     * Сигнал завершения работы клиентского приложения
     */
    private boolean stopSignal;
    /**
     * Сигнал требования ввода элемента {@link lab6.items.MusicBand} как аргумент команды {@link Command}
     */
    private boolean needElement;
    private final long id;
    private static long idPointer = 0;

    public ServerRequest() {
        id = ++idPointer;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStopSignal() {
        return stopSignal;
    }

    public void setStopSignal(boolean stopSignal) {
        this.stopSignal = stopSignal;
    }

    public boolean isNeedElement() {
        return needElement;
    }

    public void setNeedElement(boolean needElement) {
        this.needElement = needElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerRequest that = (ServerRequest) o;
        return stopSignal == that.stopSignal && needElement == that.needElement && id == that.id && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, stopSignal, needElement, id);
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "message='" + message + '\'' +
                ", stopSignal=" + stopSignal +
                ", needElement=" + needElement +
                ", id=" + id +
                '}';
    }
}
