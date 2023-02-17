package com.radiad.lab2;

public class Row {
    private double x;
    private double y;
    private double r;
    private String result;
    private String startTime;
    private String endTime;
    private String scriptTime;
    private String pointX;
    private String pointY;

    public Row(double x, double y, double r, String result, String startTime, String endTime, String scriptTime, String pointX, String pointY) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scriptTime = scriptTime;
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScriptTime() {
        return scriptTime;
    }

    public void setScriptTime(String scriptTime) {
        this.scriptTime = scriptTime;
    }

    public String getPointX() {
        return pointX;
    }

    public void setPointX(String pointX) {
        this.pointX = pointX;
    }

    public String getPointY() {
        return pointY;
    }

    public void setPointY(String pointY) {
        this.pointY = pointY;
    }
}
