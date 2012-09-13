package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView)findViewById(R.id.view);
        textView.setText("laat s wat zien");
        Handler handler = new Handler();

    }
    public void handleMessage(){
        new Thread(new ServerWorker(textView)).start();

    }
}