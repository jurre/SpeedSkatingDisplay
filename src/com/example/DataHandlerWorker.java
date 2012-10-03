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
    private LapData lapData;
    private LapData lastLapData;
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

    public synchronized void setLapData(LapData lapData) {
        this.lapData = lapData;
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
        return lapData;
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
            if (!lapData.isEqual(lastLapData)) {
                Message message = new Message();
                message.obj = lapData;
                if (handler != null) {
                    handler.sendMessage(message);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //
                }
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
                    worker.setLapData(new LapData(input));
                    if (useSchedule) {
                        lapData.setTotalDifference(schedule.getRound(Integer.parseInt(lapData.getRoundNumber())));
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
                    while (Integer.parseInt(lapData.getRoundNumber()) >= Integer.parseInt(tempLapData.getRoundNumber())) {
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

