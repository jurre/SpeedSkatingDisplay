package com.example;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/14/12
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataHandlerWorker implements Runnable {
    private Handler handler;

    public DataHandlerWorker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("145.37.58.234", 2000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            while((input = reader.readLine()) != null) {
                Message message = new Message();
                message.obj = new LapData(input);
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
