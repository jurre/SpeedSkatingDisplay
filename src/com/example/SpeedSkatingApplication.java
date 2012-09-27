package com.example;

import android.app.Application;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/26/12
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpeedSkatingApplication extends Application {
    private Schedule schedule;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
