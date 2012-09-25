package com.example;


import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/14/12
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataHandlerWorker implements Runnable {
    private Handler handler;
    private List<LapData> skobrevList;

    public DataHandlerWorker(Handler handler) {
        this.handler = handler;
        skobrevList = new LinkedList();
        skobrevList.add(new LapData("400m;35.15;35.1"));
        skobrevList.add(new LapData("800m;1:05.79;35.1"));
        skobrevList.add(new LapData("1200m;1:36.73;35.1"));
        skobrevList.add(new LapData("1600m;2:07.95;35.1"));
        skobrevList.add(new LapData("2000m;2:39.13;35.1"));
        skobrevList.add(new LapData("2400m;3:10.28;35.1"));
        skobrevList.add(new LapData("2800m;3:41.41;35.1"));
        skobrevList.add(new LapData("3200m;4:12.62;35.1"));
        skobrevList.add(new LapData("3600m;4:43.98;35.1"));
        skobrevList.add(new LapData("4000m;5:15.25;35.1"));
        skobrevList.add(new LapData("4400m;5:46.28;35.1"));
        skobrevList.add(new LapData("4800m;6:17.44;35.1"));
        skobrevList.add(new LapData("5200m;6:48.54;35.1"));
        skobrevList.add(new LapData("5400m;7:19.73;35.1"));
        skobrevList.add(new LapData("6000m;7:50.73;35.1"));
        skobrevList.add(new LapData("6400m;8:21.80;35.1"));
        skobrevList.add(new LapData("6800m;8:53.12;35.1"));
        skobrevList.add(new LapData("7200m;9:24.50;35.1"));
        skobrevList.add(new LapData("7600m;9:55.68;35.1"));
        skobrevList.add(new LapData("8000m;10:27.00;35.1"));
        skobrevList.add(new LapData("8400m;10:58.29;35.1"));
        skobrevList.add(new LapData("8800m;11:29.42;35.1"));
        skobrevList.add(new LapData("9200m;12:00.60;35.1"));
        skobrevList.add(new LapData("9600m;12:31.27;35.1"));
        skobrevList.add(new LapData("10000m;13:02.07;35.1"));
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("145.37.95.110", 2000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            int round = 0;
            while((input = reader.readLine()) != null) {
                Message message = new Message();
                LapData lapData = new LapData(input);
                lapData.setTotalDifference(skobrevList.get(round));
                message.obj = new LapData(input);
                handler.sendMessage(message);
                round++;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
