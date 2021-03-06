
package com.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/14/12
 * Time: 1:06 PM
 */
public class LapData {

    DateFormat formatter = new SimpleDateFormat("mm:ss.SSS");
    private String distance = "";
    private String totalTime = "";
    private String lapTime = "";
    private String lapDifference = "";
    private String totalDifference = "";
    private String roundNumber = "";
    private String lane = "";

    private boolean directionRight;

    public LapData(String roundNumber, String totalTime, String lapTime, String distance) {
        this.roundNumber = roundNumber;
        this.totalTime = totalTime;
        this.lapTime = lapTime;
        this.distance = distance;
    }

    public LapData(String data) {
        String[] parsedData = data.split(";");
        this.roundNumber = parsedData[0];
        this.distance = parsedData[1];
        this.totalTime = parsedData[2];
        this.lapTime = parsedData[3];
        this.lane = parsedData[4];
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

    public String getRoundNumber() {
        return roundNumber;
    }

    public boolean getDirection() {
        return directionRight;
    }

    public String getLane() {
        return lane;
    }

    public void setDirection(boolean direction) {
        this.directionRight = direction;
    }

    public String getLapDifference() {
        return lapDifference.substring(0, lapDifference.length()-1);
    }

    public String getTotalDifference() {
        return totalDifference.substring(0,totalDifference.length()-1);
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

    // @ TODO:
    // this needs to be refactored imo.
    // there should be a constructor that takes a lapdata as argument
    // and sets both differences on creation of the object
    public void setLapDifference(LapData otherLapData) {
        lapDifference = getTimeDifference(getLapTime(), otherLapData.getLapTime());
    }

    public void setTotalDifference(LapData otherLapData) {
        totalDifference = getTimeDifference(getTotalTime(), otherLapData.getTotalTime());
    }

    public String getTimeDifference(String time1, String time2) {
        Long difference = 0L;
        Date d1;
        Date d2;
        try {
            d1 = formatter.parse(parseTimeString(time1));
            d2 = formatter.parse(parseTimeString(time2));
            difference = d1.getTime() - d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertMilliSecondsToTime(difference);
    }

    public static String convertMilliSecondsToTime(long milliseconds) {
        String prefix = "";
        long m = milliseconds;
        if (milliseconds < 0) {
            m = milliseconds - (milliseconds * 2);

            prefix = "-";
        }
        if (m > 60000) {
            return String.format("%s%d:%d.%d",
                    prefix,
                    TimeUnit.MILLISECONDS.toMinutes(m),
                    TimeUnit.MILLISECONDS.toSeconds(m) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(m)),
                    TimeUnit.MILLISECONDS.toMillis(m) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(m))
            );
        } else if (m > 1000) {
            return String.format("%s%d.%d",
                    prefix,
                    TimeUnit.MILLISECONDS.toSeconds(m),
                    TimeUnit.MILLISECONDS.toMillis(m) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(m))
            );
        } else {
            return prefix + "0."+Long.toString(m);
        }


    }

    public static String parseTimeString(String time) {
        String prefix = "";
        if (time.contains("-")) {
            time = time.split("-")[1];
            prefix = "-";
        }

        String minutes = "00";
        String seconds = "00";
        String milliseconds = "000";
        String[] split = time.split(":");
        if (split.length == 2) {
            minutes = split[0];
            seconds = split[1].split("\\.")[0];
            milliseconds = split[1].split("\\.")[1];
        } else {
            split = time.split("\\.");
            if (split.length == 2) {
                seconds = time.split("\\.")[0];
                milliseconds = time.split("\\.")[1];
            } else {
                milliseconds = time;
            }

        }

        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        if (milliseconds.length() == 1) {
            milliseconds += "00";
        }

        if(milliseconds.length() == 2) {
            milliseconds += "0";
        }

        return prefix + minutes + ":" + seconds + "." + milliseconds;
    }

    public boolean isEqual(LapData comparableLapdata) {
        if(comparableLapdata==null) {
            return false;
        }
        if(this.getDistance().equals(comparableLapdata.getDistance()) &&
           this.getLapTime().equals(comparableLapdata.getLapTime()) &&
           this.getRoundNumber().equals(comparableLapdata.getRoundNumber()) &&
           this.getTotalTime().equals(comparableLapdata.getTotalTime())){
            return true;
        }
        else {
            return false;
        }
    }

    public void setHardTotalDifference(String difference) {
        this.totalDifference = difference;
    }


}

