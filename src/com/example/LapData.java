package com.example;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private Date difference;


    public LapData(String data) {
        String[] parsedData = data.split(";");
        this.distance = parsedData[0];
        this.totalTime = parsedData[1];
        this.lapTime = parsedData[2];
    }

    /**
     * Gets the distance the skater has skated up to this lap
     * @return formatted String containing the distance skated
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Gets the total time the skater has
     * @return
     */
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

    public Date getDifference(){
        return difference;
    }

    public void setDifference(String otherLapData){
        Date ownLapDate = null;
        Date otherSkaterLapDate = null;
        try {

            DateFormat formatter = new SimpleDateFormat("mm-ss-SS");
            otherSkaterLapDate = (Date)formatter.parse(otherLapData);
            ownLapDate = (Date)formatter.parse(getLapTime()) ;
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.difference =  new Date(ownLapDate.getTime() - otherSkaterLapDate.getTime());

    }
}
