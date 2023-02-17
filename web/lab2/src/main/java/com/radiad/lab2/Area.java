package com.radiad.lab2;

public class Area {
    private double radius;
    private String stringRadius;
    private boolean isValid;
    private String warning;

    public Area(String radius) {
        stringRadius = radius;
        validateRadius();
    }

    public String checkHit(Point point) {
        String result;
        if (point.isValid() && isValid) {
            result = "miss";
            double x = point.getX();
            double y = point.getY();

            if ((x >= 0 && y >= 0 && x + y <= radius / 2) ||
                    (x <= 0 && y <= 0 && Math.sqrt(x * x + y * y) <= radius / 2) ||
                    (x >= 0 && y <= 0 && x <= radius / 2 && y >= -radius)) result = "hit";

            return result;
        }
        else if (!point.isValid()) return point.getWarning();
        else return warning;
    }

    private void validateRadius() {
        isValid = false;
        try {
            radius = Double.parseDouble(stringRadius);
            if (!(1 <= radius && radius <= 5)){
                warning = "неверный диапазон радиуса";
                return;
            }
        } catch (NumberFormatException e) {
            warning = "радиус должен быть целым числом";
            return;
        }
        isValid = true;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getWarning() {
        return warning;
    }
}
