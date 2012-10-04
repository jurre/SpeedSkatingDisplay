package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 11:50 AM
 */
public class ScheduleListActivity extends Activity implements View.OnClickListener {
    SpeedSkatingApplication application;
    Context context;
    ListView listView;
    ArrayList<Schedule> scheduleArrayList;

    // we're loading the schedules from a JSON file
    // in a separate thread so it won't clog up the UI
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            // we're receiving an arraylist with all the schedules that
            // are in the schedules.json file
            scheduleArrayList = (ArrayList<Schedule>) msg.obj;

            listView.setAdapter(new ScheduleAdapter(context, android.R.layout.simple_list_item_1, scheduleArrayList));

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules);

        application = (SpeedSkatingApplication) getApplication();
        context = this;
        listView = (ListView) findViewById(R.id.list);
    }

    protected void onStart() {
        super.onStart();
        // hackidy hack
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new ScheduleListLoaderWorker(handler)).start();
    }

    // open the LapDataOverview activity with the selected schedule
    // to compare against
    @Override
    public void onClick(View v) {
        if((Integer) v.getTag() == 0) {
            application.setSchedule(null);
        }
        else {
            application.setSchedule(scheduleArrayList.get((Integer) v.getTag()));
        }
        startActivity(new Intent(ScheduleListActivity.this, LapDataOverviewActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SharedMenu.onCreateOptionsMenu(menu, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedMenu.onOptionsItemSelected(item, this);
        return true;
    }

}

