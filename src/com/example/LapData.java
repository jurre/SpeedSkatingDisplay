package com.example;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/14/12
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class LapData {

    private String distance;
    private String totalTime;
    private String lapTime;
    private boolean directionRight;


    public LapData(String data) {
        String[] parsedData = data.split(";");
        this.distance = parsedData[0];
        this.totalTime = parsedData[1];
        this.lapTime = parsedData[2];
    }

    public String getDistance() {
        return distance;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getLapTime() {
        return lapTime;
    }

    public boolean getDirection() {
        return directionRight;
    }

    public void setDirection(boolean direction) {
        this.directionRight = direction;
    }
}
