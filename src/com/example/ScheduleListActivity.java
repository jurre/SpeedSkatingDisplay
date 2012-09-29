package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
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
public class ScheduleListActivity extends Activity implements View.OnClickListener {
    SpeedSkatingApplication application;
    Context context;
    ListView listView;
    ArrayList<Schedule> scheduleArrayList;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

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
        new Thread(new ScheduleListLoaderWorker(handler, this)).start();
    }

    @Override
    public void onClick(View v) {
        application.setSchedule(scheduleArrayList.get((Integer) v.getTag()));

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

