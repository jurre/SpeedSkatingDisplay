package com.example;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class SkaterView extends Activity {

	ImageView arrow;
	TextView difference;
	TextView roundTime;
	TextView totalTime;
	TextView distance;
	TextView roundNumber;
	
	 Boolean right = true;

	    // handler to receive messages from the background thread
	    Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            // get the bundle and extract data by key
	            LapData data = (LapData)msg.obj;

	            distance.setText(data.getDistance());
	            roundTime.setText(data.getLapTime());
	            totalTime.setText(data.getTotalTime());
	            difference.setText(data.getTotalDifference());
	            //roundNumber.setText(data.getRoundNumber());

	            if (right) {
	                arrow.setImageResource(R.drawable.arrow);
	                right = false;
	            } else {
	                arrow.setImageResource(R.drawable.arrow_left);
	                right = true;
	            }
	        }
	    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skater_view);
        arrow = (ImageView) findViewById(R.id.arrow);
        difference = (TextView) findViewById(R.id.difference);
        roundTime = (TextView) findViewById(R.id.roundTime);
        totalTime = (TextView) findViewById(R.id.totalTime);
        distance = (TextView) findViewById(R.id.distance);
        roundNumber = (TextView) findViewById(R.id.roundNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_skater_view, menu);
        return true;
    }
}
