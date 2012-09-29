package com.example;

import android.app.Application;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/26/12
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpeedSkatingApplication extends Application {
    private Schedule schedule;
    private static Context context;

    public void onCreate(){
        super.onCreate();
        SpeedSkatingApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return SpeedSkatingApplication.context;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
