package com.radiad.lab2;

public class Point {
    private double x;
    private double y;
    private String stringX;
    private String stringY;
    private boolean isValid;
    private String warning;

    public Point(String stringX, String stringY) {
        this.stringX = stringX;
        this.stringY = stringY;
        this.validatePoint();
    }

    private boolean validateX() {
        try {
            x = Double.parseDouble(stringX);
            if (!(-5 <= x && x <= 3)) {
                warning = "неверный диапазон x";
                return false;
            }
        } catch (NumberFormatException e) {
            warning = "x должен быть числом";
            return false;
        }
        return true;
    }

    private boolean validateY() {
        try {
            y = Double.parseDouble(stringY);
            if (!(-5 <= y && y <= 3)) {
                warning = "неверный диапазон y";
                return false;
            }
        } catch (NumberFormatException e) {
            warning = "y должен быть числом";
            return false;
        }
        return true;
    }

    private void validatePoint() {
        this.isValid = this.validateX() && this.validateY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getWarning() {
        return warning;
    }
}
