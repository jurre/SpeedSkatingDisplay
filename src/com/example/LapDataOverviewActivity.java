package com.example;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class LapDataOverviewActivity extends Activity {

    TextView distanceView;
    TextView lapTimeView;
    TextView totalTimeView;
    TextView differenceView;
    TextView roundNumberView;
    ImageView arrow;
    SpeedSkatingApplication application;

    Boolean right = true;

    // handler to receive messages from the background thread
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            LapData data = (LapData) msg.obj;

            distanceView.setText(data.getDistance());
            lapTimeView.setText(data.getLapTime());
            totalTimeView.setText(data.getTotalTime());
            differenceView.setText(data.getTotalDifference());
            roundNumberView.setText("Ronde: " + data.getRoundNumber());

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("arrowRight", R.drawable.arrow);
            map.put("arrowLeft", R.drawable.arrow_left);

            if (right) {
                arrow.setImageResource(map.get("arrowRight"));
                right = false;
            } else {
                arrow.setImageResource(map.get("arrowLeft"));
                right = true;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        application = (SpeedSkatingApplication) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lap_data);
        setTitle("Schema: " + application.getSchedule().getName());
        distanceView = (TextView) findViewById(R.id.distance);
        roundNumberView = (TextView) findViewById(R.id.roundNumber);
        lapTimeView = (TextView) findViewById(R.id.lapTime);
        totalTimeView = (TextView) findViewById(R.id.totalTime);
        arrow = (ImageView) findViewById(R.id.imageView);
        differenceView = (TextView) findViewById(R.id.difference);
    }

    protected void onStart() {
        super.onStart();
        new Thread(new DataHandlerWorker(handler, application.getSchedule())).start();
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