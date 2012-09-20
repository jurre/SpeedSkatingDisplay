package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    TextView distanceView;
    TextView lapTimeView;
    TextView totalTimeView;
    TextView differenceView;
    ImageView arrow;

    Boolean right = true;

    // handler to receive messages from the background thread
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            LapData data = (LapData) msg.obj;
            String distance = data.getDistance();
            String lapTime = data.getLapTime();
            String totalTime = data.getTotalTime();
            String totaldifference = data.getTotalDifference();

            distanceView.setText(distance);
            lapTimeView.setText(lapTime);
            totalTimeView.setText(totalTime);
            differenceView.setText(totaldifference);

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        distanceView = (TextView) findViewById(R.id.distance);
        lapTimeView = (TextView) findViewById(R.id.lapTime);
        totalTimeView = (TextView) findViewById(R.id.totalTime);
        arrow = (ImageView) findViewById(R.id.imageView);
        differenceView = (TextView) findViewById(R.id.difference);
    }

    protected void onStart() {
        super.onStart();
        new Thread(new DataHandlerWorker(handler)).start();
    }
}