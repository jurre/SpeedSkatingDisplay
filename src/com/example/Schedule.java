package com.example;

import android.content.Context;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 10:20 AM
 */
public class Schedule {

    private List<LapData> lapData;
    private String name;

    public Schedule(List<LapData> lapData, String name) {
        this.lapData = lapData;
        this.setName(name);
    }

    public LapData getRound(int roundNumber) {
        return lapData.get(roundNumber);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<LapData> getLapData() {
        return lapData;
    }


    public void writeToJSON(){
        new Thread(new ScheduleWriterWorker(new Schedule(this.lapData, this.getName()))).start();
    }
}
