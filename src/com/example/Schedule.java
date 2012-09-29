package com.example;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Schedule {
    private List<LapData> lapData;
    private String name;

    public Schedule(List<LapData> lapData, String name) {
        this.lapData = lapData;
        this.setName(name);
    }

    public LapData getRound(int roundNumber) {
        return lapData.get(roundNumber);
    }

    public String[] getRoundNumbers() {
        String[] rounds = new String[lapData.size()];
        for (int i = 0; i < lapData.size(); i++) {
            rounds[i] = lapData.get(i).getRoundNumber();
        }
        return rounds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<LapData> dummyData() {
        List<LapData> data = new LinkedList<LapData>();
        data.add(new LapData("1;400m;35.15;35.1"));
        data.add(new LapData("2;800m;1:05.79;30.6"));
        data.add(new LapData("3;1200m;1:36.73;30.9"));
        data.add(new LapData("4;1600m;2:07.95;31.2"));
        data.add(new LapData("5;2000m;2:39.13;31.1"));
        data.add(new LapData("6;2400m;3:10.28;31.1"));
        data.add(new LapData("7;2800m;3:41.41;31.1"));
        data.add(new LapData("8;3200m;4:12.62;31.2"));
        data.add(new LapData("9;3600m;4:43.98;31.3"));
        data.add(new LapData("10;4000m;5:15.25;31.2"));
        data.add(new LapData("11;4400m;5:46.28;31.0"));
        data.add(new LapData("12;4800m;6:17.44;31.1"));
        data.add(new LapData("13;5200m;6:48.54;31.1"));
        data.add(new LapData("14;5600m;7:19.73;31.1"));
        data.add(new LapData("15;6000m;7:50.73;31.0"));
        data.add(new LapData("16;6400m;8:21.80;31.0"));
        data.add(new LapData("17;6800m;8:53.12;31.3"));
        data.add(new LapData("18;7200m;9:24.50;31.3"));
        data.add(new LapData("19;7600m;9:55.68;31.1"));
        data.add(new LapData("20;8000m;10:27.00;31.3"));
        data.add(new LapData("21;8400m;10:58.29;31.2"));
        data.add(new LapData("22;8800m;11:29.42;31.1"));
        data.add(new LapData("23;9200m;12:00.60;31.1"));
        data.add(new LapData("24;9600m;12:31.27;30.6"));
        data.add(new LapData("25;10000m;13:02.07;30.8"));

        return data;
    }

}
