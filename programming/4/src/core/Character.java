package core;

import java.util.Objects;

public class Character implements Movable, Soundable{
    private String name;
    private Location location;

    public Character(){};
    public Character(String name) {
        this.name = name;
        location = new Location();
    }
    public Character(String name, int xCoordinate, int yCoordinate) {
        this.name = name;
        location = new Location(xCoordinate, yCoordinate);
    }

    public String getName() {
        return name;
    }

    public void addxCoordinate(int xDisplacement) {
        if (xDisplacement < 0){
            throw new MyException(name, xDisplacement);
        }
        this.location.xCoordinate += xDisplacement;
    }

    public void addyCoordinate(int yDisplacement) {
        this.location.yCoordinate += yDisplacement;
    }

    @Override
    public void moving(Move move){
        move.doMove(this);
    }

    @Override
    public void makeSound(Sound sound){
        System.out.println(name + " " + sound.getSound());
    }

    private class Location{
        public int xCoordinate;
        public int yCoordinate;

        public Location() {
            xCoordinate = 0;
            yCoordinate = 0;
        }

        public Location(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }
    }

    @Override
    public String toString(){
        if (this.name == null || "".equals(this.name)){
            return "Безымянное нечто находится в точке (" + this.location.xCoordinate + ";" + this.location.yCoordinate + ")";
        }
        return "Существо " + this.name + " находится в точке (" + this.location.xCoordinate + ";" + this.location.yCoordinate + ")";
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
        return Objects.hash(name, location.xCoordinate, location.yCoordinate);
    }
}