package com.example;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleListActivity extends ListActivity implements View.OnClickListener {

    SpeedSkatingApplication application;
    ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules);

        application = (SpeedSkatingApplication)getApplication();

        Schedule schedule = new Schedule(Schedule.dummyData(), "Skobrev");
        Schedule schedule2 = new Schedule(Schedule.dummyData(), "Goud");

        scheduleList.add(schedule);
        scheduleList.add(schedule2);
        scheduleList.add(application.getSchedule());
        setListAdapter(new ScheduleAdapter(this, android.R.layout.simple_list_item_1, scheduleList));
    }

    @Override
    public void onClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("BRO");
        alertDialog.setMessage("Check it: " + scheduleList.get((Integer) v.getTag()).getName());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}

