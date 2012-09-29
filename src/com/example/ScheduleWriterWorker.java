package com.example;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/29/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ScheduleWriterWorker implements Runnable {
    private Schedule schedule;
    private JSONArray jsonArray;

    public ScheduleWriterWorker(Schedule schedule) {
        this.schedule = schedule;

        try {
            // load the JSON file with schedules
            InputStream inputStream = SpeedSkatingApplication.getAppContext().getResources().openRawResource(R.raw.schedules);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonArray = new JSONArray(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void writeSchedule() {
        JSONObject scheduleObject = new JSONObject();
        JSONObject lapDataObject = new JSONObject();
        for (LapData lapData : schedule.getLapData()) {
            writeLapData(lapDataObject, lapData);
        }
        try {
            scheduleObject.put("name", schedule.getName());
            scheduleObject.put("lapData", lapDataObject);
            Log.e("scheduleObject = ", scheduleObject.toString());
            jsonArray.put(scheduleObject);
            Log.e("Output stream = ", jsonArray.toString());
            FileOutputStream fileOutputStream = SpeedSkatingApplication.getAppContext().openFileOutput("schedules.json", Context.MODE_APPEND);
            fileOutputStream.write(jsonArray.toString().getBytes());
            fileOutputStream.close();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeLapData(JSONObject jsonObject, LapData lapData) {
        try {
            jsonObject.put("roundNumber", lapData.getRoundNumber());
            jsonObject.put("distance", lapData.getDistance());
            jsonObject.put("lapTime", lapData.getLapTime());
            jsonObject.put("totalTime", lapData.getTotalTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        writeSchedule();
    }

}
