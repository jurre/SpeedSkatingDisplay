package com.example;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/29/12
 * Time: 11:47 PM
 */
public class ScheduleListLoaderWorker implements Runnable {
    private Handler handler;

    public ScheduleListLoaderWorker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
        String jsonRep = null;

        try {
            // load the JSON file with schedules
            InputStream inputStream = SpeedSkatingApplication.getAppContext().openFileInput("schedules.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonRep = stringBuilder.toString();
        } catch (Throwable throwable) {
            Log.v("LoadError", throwable.toString());
        }

        try {
            JSONArray jsonArray;
            if (jsonRep == null) {
               jsonArray = new JSONArray();
            } else {
               jsonArray = new JSONArray(jsonRep);
            }
            List<LapData> lapDataList = new ArrayList<LapData>();

            // iterate over all schedule objects in the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonSchedule = jsonArray.getJSONObject(i);

                // get each schedule's LapData array and create LapData objects from all of them
                JSONArray jsonLapDataArray = jsonSchedule.getJSONArray("lapData");

                for (int j = 0; j < jsonLapDataArray.length(); j++) {
                    JSONObject jsonLapData = jsonLapDataArray.getJSONObject(j);
                    String roundNumber = jsonLapData.get("roundNumber").toString();
                    String distance = jsonLapData.get("distance").toString();
                    String lapTime = jsonLapData.get("lapTime").toString();
                    String totalTime = jsonLapData.get("totalTime").toString();
                    LapData lapData = new LapData(roundNumber, totalTime, lapTime, distance);
                    lapDataList.add(lapData);
                }
                // add the schedules name and append it to the list with schedules
                Schedule schedule = new Schedule(lapDataList, jsonSchedule.get("name").toString());
                scheduleArrayList.add(schedule);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // once the list with schedules is complete send it to the activity
        Message message = new Message();
        Log.v("ArrayList loaded = ", scheduleArrayList.toString());
        message.obj = scheduleArrayList;
        handler.sendMessage(message);
    }
}
