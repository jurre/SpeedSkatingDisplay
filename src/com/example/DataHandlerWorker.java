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
 */
public class DataHandlerWorker implements DataHandlerInterface {
    private Handler handler;
    private Schedule schedule;
    private LapData lapData;
    private LapData lastLapData;
    private boolean useSchedule;
    private String serverIp;
    private int portMySkater;
    private int portOpponentSkater;
    private boolean running = true;


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
    public DataHandlerWorker(Handler handler, Schedule schedule) {
        if (handler != null) {
            this.handler = handler;
        }
        this.serverIp = "145.37.58.131";
        this.portMySkater = 2000;
        this.portOpponentSkater = 3000;
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
            MySkaterDataRetriever mySkaterDataRetriever = new MySkaterDataRetriever(this, serverIp, portMySkater);
            new Thread(mySkaterDataRetriever).start();


            if (!useSchedule) {
                OpponentSkaterDataRetriever opponentSkaterDataRetriever = new OpponentSkaterDataRetriever(this, serverIp, portOpponentSkater);
                new Thread(opponentSkaterDataRetriever).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LapData getLapData() {
        return lapData;
    }

    public void setLastLapdata(LapData lapData) {
        this.lastLapData = lapData;
    }

    public void sendData() {
        if (handler !=null) {
            Message message = new Message();
            message.obj = lapData;

            handler.sendMessage(message);
        }
    }



    private class MySkaterDataRetriever implements Runnable {
        Socket socket;
        DataHandlerWorker worker;
        String ip;
        int port;

        public MySkaterDataRetriever(DataHandlerWorker worker, String ip, int port) {
            this.ip = ip;
            this.port = port;
            this.worker = worker;
        }

        @Override
        public void run() {
            try {
                this.socket = new Socket(ip, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Thread started");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input;
                while ((input = reader.readLine()) != null) {
                    if(worker.getLapData()!=null) {
                        worker.setLastLapdata(worker.getLapData());
                    }
                    worker.setLapData(new LapData(input));
                    if (useSchedule) {
                        lapData.setTotalDifference(schedule.getRound(Integer.parseInt(lapData.getRoundNumber())));
                        worker.sendData();
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
        String ip;
        int port;

        public OpponentSkaterDataRetriever(DataHandlerWorker worker, String ip, int port) {
            this.ip = ip;
            this.port = port;
            this.worker = worker;
        }

        @Override
        public void run() {
            System.out.println("Thread started");
            try {
                this.socket = new Socket(ip, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input;
                while ((input = reader.readLine()) != null) {
                    LapData tempLapData = new LapData(input);
                    boolean differenceCalculated =false;
                    System.out.println("OpponentLine received");
                    while(differenceCalculated==false) {
                        if(worker.getLapData() != null) {
                            if(Integer.parseInt(worker.getLapData().getRoundNumber()) >= Integer.parseInt(tempLapData.getRoundNumber())){
                                worker.getLapData().setTotalDifference(tempLapData);
                                differenceCalculated = true;
                                worker.sendData();
                            }
                            else {
                                System.out.println("WAITING");
                                try {
                                    Thread.sleep(1000);
                                } catch(InterruptedException e) {

                                }
                            }
                        }
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

