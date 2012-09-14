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

    TextView textView;
    // handler to receive messages from the background thread
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            LapData data = (LapData)msg.obj;
            String key = data.getDistance();
            textView.setText(key);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.view);
        textView.setText("Distance");
    }



    protected void onStart() {
        super.onStart();
        new Thread(new DataHandlerWorker(handler)).start();
    }
}