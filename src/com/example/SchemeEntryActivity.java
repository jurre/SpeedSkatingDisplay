package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/21/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchemeEntryActivity extends Activity {
    private ArrayList<LapData> lapData = new ArrayList<LapData>();
    private EditText schemeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LapData lapData1 = new LapData("1;400m;1:08.32;31.2");
        LapData lapData2 = new LapData("2;800m;1:06.22;32.2");

        this.lapData.add(lapData1);
        this.lapData.add(lapData2);
        final Schedule schedule = new Schedule(this.lapData, "test");



        setContentView(R.layout.scheme_entry);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(new SchemeEntryAdapter(this, schedule, schedule.getRoundNumbers()));

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("onclick clicked", "");
                schedule.writeToJSON();
            }
        });

    }

    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    } */

    private void saveSchedule(String name) {
        if (name.equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Vul een schemanaam in.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

        }
        Schedule schedule = new Schedule(lapData, name);
        schedule.writeToJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SharedMenu.onCreateOptionsMenu(menu, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedMenu.onOptionsItemSelected(item, this);
        return true;
    }

    public void updateLapTime(int position, String lapTime) {
        double d = Double.valueOf(lapTime);

        int pos = position;
        lapData.get(position).setLapTime((long) d * 1000);
    }
}
