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
 */

public class ScheduleWriterWorker implements Runnable {
    private Schedule schedule;
    private JSONArray jsonArray;

    public ScheduleWriterWorker(Schedule schedule) {
        this.schedule = schedule;

        try {
            // load the JSON file with schedules
            InputStream inputStream = SpeedSkatingApplication.getAppContext().openFileInput("schedules.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            if (stringBuilder.length() != 0) {
                jsonArray = new JSONArray(stringBuilder.toString());
            }
            inputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            jsonArray = new JSONArray();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void writeSchedule() {
        JSONObject scheduleObject = new JSONObject();
        JSONArray lapDataArray = new JSONArray();
        for (LapData lapData : schedule.getLapData()) {
            lapDataArray.put(getLapDataJSON(lapData));
        }
        try {
            scheduleObject.put("name", schedule.getName());
            scheduleObject.put("lapData", lapDataArray);
            if (jsonArray == null) {
                String s = scheduleObject.toString();
                jsonArray = new JSONArray(s);
            } else {
                jsonArray.put(scheduleObject);
            }
            FileOutputStream fileOutputStream = SpeedSkatingApplication.getAppContext().openFileOutput("schedules.json", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(jsonArray.toString().toCharArray());
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public JSONObject getLapDataJSON(LapData lapData) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roundNumber", lapData.getRoundNumber());
            jsonObject.put("distance", lapData.getDistance());
            jsonObject.put("lapTime", lapData.getLapTime());
            jsonObject.put("totalTime", lapData.getTotalTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void run() {
        writeSchedule();
    }

}
