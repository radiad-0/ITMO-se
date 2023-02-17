package core;

import java.util.Objects;

public class Character implements Movable, Soundable{
    private String name;
    private int xCoordinate = 0;
    private int yCoordinate = 0;

    public Character(String name) {
        this.name = name;
    }
    public Character(String name, int xCoordinate, int yCoordinate) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void addxCoordinate(int xDisplacement) {
        this.xCoordinate += xDisplacement;
    }

    public void addyCoordinate(int yDisplacement) {
        this.yCoordinate += yDisplacement;
    }

    @Override
    public void moving(Move move){
        move.doMove(this);
    }

    @Override
    public void makeSound(Sound sound){
        System.out.println(name + " " + sound.getSound());
    }

    @Override
    public String toString(){
        if (this.name == null || this.name.equals("")){
            return "Безымянное нечто находится в точке (" + this.xCoordinate + ";" + this.yCoordinate + ")";
        }
        return "Существо " + this.name + " находится в точке (" + this.xCoordinate + ";" + this.yCoordinate + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Character) {
            return this.name.equals(((Character) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, xCoordinate, yCoordinate);
    }
}