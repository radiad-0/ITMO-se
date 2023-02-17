package lab6.items;

import java.io.Serializable;

/**
 * Класс описывающий координаты на плоскости
 */
public class Coordinates implements Serializable {
    private int x;
    private double y;

    public Coordinates(int x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "\n\t\tx=" + x +
                "\n\t\ty=" + y +
                "\n\t}";
    }
}
