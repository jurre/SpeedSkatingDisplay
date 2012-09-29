package com.example;

import android.content.Context;
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
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleListLoaderWorker implements Runnable {
    private Handler handler;
    private Context context;

    public ScheduleListLoaderWorker(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
        String jsonRep = null;

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.schedules);
            if (inputStream != null) {
                Writer writer = new StringWriter();
                char[] buffer = new char[1024];
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }
                } finally {
                    inputStream.close();
                }
                jsonRep = writer.toString();
            }
            else {
                Log.v("LoadError", "Problem opening the file");
            }
        } catch (Throwable throwable) {
            Log.v("LoadError", throwable.toString());
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonRep);
            List<LapData> lapDataList = new ArrayList<LapData>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonSchedule = jsonArray.getJSONObject(i);
                JSONArray jsonLapDataArray = (JSONArray) jsonSchedule.get("lapData");
                for (int j = 0; j < jsonLapDataArray.length(); j++) {
                    JSONObject jsonLapData = jsonLapDataArray.getJSONObject(j);
                    String roundNumber = jsonLapData.get("roundNumber").toString();
                    String distance = jsonLapData.get("distance").toString();
                    String lapTime = jsonLapData.get("lapTime").toString();
                    String totalTime = jsonLapData.get("totalTime").toString();
                    LapData lapData = new LapData(roundNumber, totalTime, lapTime, distance);
                    lapDataList.add(lapData);
                }
                Schedule schedule = new Schedule(lapDataList, jsonSchedule.get("name").toString());
                scheduleArrayList.add(schedule);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.obj = scheduleArrayList;
        handler.sendMessage(message);
    }
}
