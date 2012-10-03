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
    private LapData lapdata;
    private LapData lastlapdata;
    private boolean useSchedule;
    private String serverIp;
    private int portMySkater;
    private int portOpponentSkater;


    public DataHandlerWorker(Handler handler, Schedule schedule, String serverIp, int portMySkater, int portOpponentSkater) {
        if (handler != null) {
            this.handler = handler;
        }
        this.serverIp = serverIp;
        this.portMySkater = portMySkater;
        this.portOpponentSkater = portOpponentSkater;
        if (schedule != null) {
            this.schedule = schedule;
        } else {
            useSchedule = false;
        }
        initDataRetrievers();
    }

    public synchronized void setLapdata(LapData lapdata) {
        this.lapdata = lapdata;
    }

    public void initDataRetrievers() {
        try {
            Socket socketMySkater = new Socket(serverIp, portMySkater);

            MySkaterDataRetriever mySkaterDataRetriever = new MySkaterDataRetriever(socketMySkater, this);
            new Thread(mySkaterDataRetriever).start();


            if (!useSchedule) {
                Socket socketOpponent = new Socket(serverIp, portOpponentSkater);
                OpponentSkaterDataRetriever opponentSkaterDataRetriever = new OpponentSkaterDataRetriever(socketOpponent, this);
                new Thread(opponentSkaterDataRetriever).start();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public LapData getLapData() {
        return lapdata;
    }

    public DataHandlerWorker(Handler handler, Schedule schedule) {
        if (handler != null) {
            this.handler = handler;
        }
        this.serverIp = "localhost";
        this.portMySkater = 2000;
        this.portOpponentSkater = 3000;
        if (schedule != null) {
            this.schedule = schedule;
        } else {
            useSchedule = false;
        }
        initDataRetrievers();
    }

    @Override
    public void run() {

        while (true) {
            if (!lapdata.isEqual(lastlapdata)) {
                Message message = new Message();
                message.obj = lapdata;
                if (handler != null) {
                    handler.sendMessage(message);
                }
                lastlapdata = lapdata;
            }

        }

    }


    private class MySkaterDataRetriever implements Runnable {
        Socket socket;
        DataHandlerWorker worker;

        public MySkaterDataRetriever(Socket socket, DataHandlerWorker worker) {
            this.socket = socket;
            this.worker = worker;
        }

        @Override
        public void run() {
            System.out.println("Thread started");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input;
                while ((input = reader.readLine()) != null) {
                    worker.setLapdata(new LapData(input));
                    if (useSchedule) {
                        lapdata.setTotalDifference(schedule.getRound(Integer.parseInt(lapdata.getRoundNumber())));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Thread ended");
        }

    }


    private class OpponentSkaterDataRetriever implements Runnable {
        Socket socket;
        DataHandlerWorker worker;

        public OpponentSkaterDataRetriever(Socket socket, DataHandlerWorker worker) {
            this.socket = socket;
            this.worker = worker;
        }

        @Override
        public void run() {
            System.out.println("Thread started");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input;
                while ((input = reader.readLine()) != null) {
                    LapData tempLapData = new LapData(input);
                    System.out.println("OpponentLine received");
                    while(Integer.parseInt(lapdata.getRoundNumber())>=Integer.parseInt(tempLapData.getRoundNumber()))
                    {
                        //wait for myskater to send its lap before calculating the difference.
                    }
                    if (worker.getLapData() != null) {
                        worker.getLapData().setTotalDifference(tempLapData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Thread ended");
        }

    }


}

