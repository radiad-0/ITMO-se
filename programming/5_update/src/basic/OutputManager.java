package basic;

public class OutputManager {
    private Boolean manualMode;

    public OutputManager() {
        manualMode = true;
    }

    public OutputManager(Boolean manualMode) {
        this.manualMode = manualMode;
    }

    public void print_LN_ManualModeSimpleMessage(Object message){
        if (manualMode) System.out.println(message.toString());
    }

    public void printManualModeSimpleMessage(Object message){
        if (manualMode) System.out.print(message.toString());
    }

    public void print_LN_ScriptModeSimpleMessage(Object message){
        if (!manualMode) System.out.println("\033[35m" + message + "\033[0m");
    }

    public void printScriptModeSimpleMessage(Object message){
        if (!manualMode) System.out.print("\033[35m" + message + "\033[0m");
    }

    public void print_LN_ManualModeHighlightedMessage(Object message){
        print_LN_ManualModeSimpleMessage("\033[35m" + (message) + "\033[0m");
    }

    public void printManualModeHighlightedMessage(Object message){
        printManualModeSimpleMessage("\033[35m" + message + "\033[0m");
    }

    public void print_LN_ScriptModeHighlightedMessage(Object message){
        print_LN_ScriptModeSimpleMessage("\033[35m" + message + "\033[0m");
    }

    public void printScriptModeHighlightedMessage(Object message){
        printScriptModeSimpleMessage("\033[35m" + message + "\033[0m");
    }

    public void print_LN_ManualModeError(Object message){
        print_LN_ManualModeSimpleMessage("\033[31m" + message + "\033[0m");
    }

    public void printManualModeError(Object message){
        printManualModeSimpleMessage("\033[31m" + message + "\033[0m");
    }

    public void print_LN_ScriptModeError(Object message){
        print_LN_ScriptModeSimpleMessage("\033[31m" + message + "\033[0m");
    }

    public void printScriptModeError(Object message){
        printScriptModeSimpleMessage("\033[31m" + message + "\033[0m");
    }

    public void setManualMode() {
        manualMode = true;
    }

    public void setScriptMode(){
        manualMode = false;
    }

    public boolean isManualMode(){
        return manualMode;
    }

    public boolean isScriptMode(){
        return !manualMode;
    }
}
