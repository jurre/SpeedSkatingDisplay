package com.example;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class LapDataOverviewActivity extends Activity {

    TextView distanceView;
    TextView lapTimeView;
    TextView totalTimeView;
    TextView differenceView;
    ImageView arrow;
    SpeedSkatingApplication application;

    Boolean right = true;

    // handler to receive messages from the background thread
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            LapData data = (LapData)msg.obj;
            String distance = data.getDistance();
            String lapTime = data.getLapTime();
            String totalTime = data.getTotalTime();
            String totalDifference = data.getTotalDifference();

            distanceView.setText(distance);
            lapTimeView.setText(lapTime);
            totalTimeView.setText(totalTime);
            differenceView.setText(totalDifference);

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
        application = (SpeedSkatingApplication)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lap_data);
        setTitle("Schema: " + application.getSchedule().getName());
        distanceView = (TextView)findViewById(R.id.distance);
        lapTimeView = (TextView)findViewById(R.id.lapTime);
        totalTimeView = (TextView)findViewById(R.id.totalTime);
        arrow = (ImageView)findViewById(R.id.imageView);
        differenceView = (TextView)findViewById(R.id.difference);
    }

    protected void onStart() {
        super.onStart();
        new Thread(new DataHandlerWorker(handler, application.getSchedule())).start();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_schema:
                setContentView(R.layout.schedules);
                return true;
            case R.id.menu_add:
            	//setContentView(R.layout.add);
            	return true;
            case R.id.menu_home:
            	setContentView(R.layout.lap_data);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}