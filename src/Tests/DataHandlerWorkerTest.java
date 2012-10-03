package Tests;

import com.example.*;
import org.junit.*;
import android.os.Handler;
import android.os.Message;

import java.net.*;
import java.io.*;
import java.util.ArrayList;


import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 10/2/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataHandlerWorkerTest {


    @Test
    public void DataHandlerWorker() {


        new Thread(new TMTS(3000, "C:\\Users\\Marthyn\\Documents\\GitHub\\SpeedSkatingDisplay\\SpeedSkatingServer\\SkobrevOlympics10000.csv")).start();
        new Thread(new TMTS(2000, "C:\\Users\\Marthyn\\Documents\\GitHub\\SpeedSkatingDisplay\\SpeedSkatingServer\\KramerOlympics10000.csv")).start();
        DataHandlerWorker worker = new DataHandlerWorker(null, null, "localhost", 2000, 3000);
        LapData lastLapData = null;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            LapData lapdata = worker.getLapData();
            if (lapdata!=null) {

                System.out.println("not null");
                System.out.println(lapdata.getRoundNumber()+" : "+lapdata.getLapTime() + " : " + lapdata.getTotalTime() + " : " + lapdata.getTotalDifference());
                lastLapData = lapdata;
            }

        }


    }

    private class TMTS implements Runnable {
        private int port;
        private String filepath;

        public TMTS(int port, String filepath) {
            this.port = port;
            this.filepath = filepath;
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Thread started");
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + port);
                System.exit(1);
            }


            try {
                Socket socket = serverSocket.accept();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


                BufferedReader inputStream = new BufferedReader(new FileReader(filepath));
                String line;
                int count = 0;
                while ((line = inputStream.readLine()) != null) {
                    if (count > 1) {
                        out.println(line);
                        Double lap = Double.parseDouble(line.split(";")[3]) * 1000.00;
                        Long milliseconds = lap.longValue();
                        Thread.sleep(milliseconds);
                    }
                    count++;
                }
            } catch (IOException x) {
                System.err.println(x);
            } catch (InterruptedException x) {
                System.err.println(x.getStackTrace().toString());
            }


        }
    }


}
