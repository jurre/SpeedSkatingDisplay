package com.example;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/20/12
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleListActivity extends ListActivity implements View.OnClickListener {

    String[] schedules = {"Goud", "Zilver", "Shani Davis"};
    ArrayList<String> scheduleList = new ArrayList<String>(Arrays.asList(schedules));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules);
        setListAdapter(new ScheduleAdapter(this, android.R.layout.simple_list_item_1, scheduleList));
    }

    @Override
    public void onClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("BRO");
        alertDialog.setMessage("Check it: " + schedules[(Integer) v.getTag()]);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
