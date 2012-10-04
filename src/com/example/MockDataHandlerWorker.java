package com.example;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 10/1/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockDataHandlerWorker implements Runnable, DataHandlerInterface {

    private Handler handler;
    private Schedule schedule;
    private ArrayList<String> laps = new ArrayList<String>();

    public MockDataHandlerWorker(Handler handler, Schedule schedule) {
        this.handler = handler;
        this.schedule = schedule;
        initLaps();
    }

    public void run() {
        int round = 0;
        for(int i = 0; i< laps.size(); i++) {
            Message message = new Message();
            LapData lapData = new LapData(laps.get(i));
            lapData.setTotalDifference(schedule.getRound(round));
            message.obj = lapData;
            handler.sendMessage(message);
            round++;
            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {

            }
        }
    }

    private void initLaps() {
        laps.add("1;400m;33.43;33.4");
        laps.add("2;800m;1:04.50;31.0");
        laps.add("3;1200m;1:35.59;31.0");
        laps.add("4;1600m;2:06.07;30.4");
        laps.add("5;2000m;2:36.49;30.4");
        laps.add("6;2400m;3:07.04;30.5");
        laps.add("7;2800m;3:37.42;30.3");
        laps.add("8;3200m;4:08.26;30.8");
        laps.add("9;3600m;4:38.96;30.7");
        laps.add("10;4000m;5:09.72;30.7");
        laps.add("11;4400m;5:40.51;30.7");
        laps.add("12;4800m;6:11.35;30.8");
        laps.add("13;5200m;6:42.37;31.0");
        laps.add("14;5600m;7:13.25;30.8");
        laps.add("15;6000m;7:44.31;31.0");
        laps.add("16;6400m;8:15.03;30.7");
        laps.add("17;6800m;8:44.95;29.9");
        laps.add("18;7200m;9:15.60;30.6");
        laps.add("19;7600m;9:46.22;30.6");
        laps.add("20;8000m;10:17.10;30.8");
        laps.add("21;8400m;10:48.18;31.0");
        laps.add("22;8800m;11:19.39;31.2");
        laps.add("23;9200m;11:50.61;31.2");
        laps.add("24;9600m;12:22.26;31.6");
        laps.add("25;10000m;12:54.50;32.2");
    }
}
