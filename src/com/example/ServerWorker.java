package com.example;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Horlings
 * Date: 13-9-12
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class ServerWorker implements Runnable {
    private Socket socket;
    private TextView textView;
    public ServerWorker( TextView textView){
        this.textView = textView;
    }

    @Override
    public void run() {

        try {
            this.socket = new Socket("145.37.62.129",2000);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            while((input = reader.readLine()) != null){
                textView.setText(input);
            }
        } catch (IOException e) {
            textView.setText(e + "");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}