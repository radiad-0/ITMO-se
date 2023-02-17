package core;

import java.util.Objects;

public abstract class Move {
    private String message;

    public Move(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public abstract void doMove(Character character);

    @Override
    public String toString(){
        if (this.message == null || "".equals(this.message)){
            return "Неизвестный Move";
        }
        return "Move в результате которого существо " + this.message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Move) {
            return this.message.equals(((Move) obj).getMessage());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
