package lab6.tools;

import java.io.File;
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
     * Сигнал требования ввода элемента {@link lab6.items.MusicBand} как аргумент команды {@link lab6.commands.Command}
     */
    private boolean needElement;
    /**
     * Режим ручного режима
     */
    private boolean setManualMode;
    /**
     * Режим исполнения скрипта
     */
    private boolean setScriptMode;
    private File scriptFile;
    private final long id;
    private static long idPointer = 0;

    {
        id = ++idPointer;
    }

    public ServerRequest() {
        message = null;
        stopSignal = false;
        needElement = false;
        setManualMode = false;
        setScriptMode = false;
        scriptFile = null;
    }

    public ServerRequest(String message, boolean stopSignal, boolean needElement) {
        this.message = message;
        this.stopSignal = stopSignal;
        this.needElement = needElement;
        setManualMode = false;
        setScriptMode = false;
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

    public boolean isSetManualMode() {
        return setManualMode;
    }

    public void setManualMode(boolean setManualMode) {
        this.setManualMode = setManualMode;
    }

    public boolean isSetScriptMode() {
        return setScriptMode;
    }

    public void setScripMode(boolean anyModeOutput) {
        this.setScriptMode = anyModeOutput;
    }

    public File getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(File scriptFile) {
        this.scriptFile = scriptFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerRequest that = (ServerRequest) o;
        return stopSignal == that.stopSignal && needElement == that.needElement && setManualMode == that.setManualMode && setScriptMode == that.setScriptMode && id == that.id && Objects.equals(message, that.message) && Objects.equals(scriptFile, that.scriptFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, stopSignal, needElement, setManualMode, setScriptMode, scriptFile, id);
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "message='" + message + '\'' +
                ", stopSignal=" + stopSignal +
                ", needElement=" + needElement +
                ", setManualMode=" + setManualMode +
                ", setScriptMode=" + setScriptMode +
                ", scriptFile=" + scriptFile +
                ", id=" + id +
                '}';
    }
}
