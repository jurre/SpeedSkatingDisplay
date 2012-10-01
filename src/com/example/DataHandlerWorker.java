package com.example;


import android.os.Handler;
import android.os.Message;

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
    private Schedule schedule;

    public DataHandlerWorker(Handler handler, Schedule schedule) {
        this.handler = handler;
        this.schedule = schedule;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("145.37.58.115", 2000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            int round = 0;
            while ((input = reader.readLine()) != null) {
                Message message = new Message();
                LapData lapData = new LapData(input);
                lapData.setTotalDifference(schedule.getRound(round));
                message.obj = lapData;
                handler.sendMessage(message);
                round++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
