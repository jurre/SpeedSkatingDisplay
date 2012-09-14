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
            Bundle b = msg.getData();
            String key = b.getString("RoundData");
            textView.setText(key);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.view);
        textView.setText("laat s wat zien");
    }



    protected void onStart() {
        super.onStart();

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("145.37.55.60", 2000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String input;
                    while((input = reader.readLine()) != null) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("RoundData", input);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

        background.start();
    }
}