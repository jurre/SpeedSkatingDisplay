package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends Activity {

    TextView distanceView;
    TextView lapTimeView;
    TextView totalTimeView;

    // handler to receive messages from the background thread
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            LapData data = (LapData)msg.obj;
            String distance = data.getDistance();
            String lapTime = data.getLapTime();
            String totalTime = data.getTotalTime();

            distanceView.setText(distance);
            lapTimeView.setText(lapTime);
            totalTimeView.setText(totalTime);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        distanceView = (TextView)findViewById(R.id.distance);
        lapTimeView = (TextView)findViewById(R.id.lapTime);
        totalTimeView = (TextView)findViewById(R.id.totalTime);
    }



    protected void onStart() {
        super.onStart();
        new Thread(new DataHandlerWorker(handler)).start();
    }
}