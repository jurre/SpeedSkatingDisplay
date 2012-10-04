package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/21/12
 * Time: 11:56 AM
 */
public class SchemeEntryActivity extends Activity {
    private ArrayList<LapData> templateList = new ArrayList<LapData>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        templateList.add(new LapData("1;400m;33.43;33.4;l"));
        templateList.add(new LapData("2;800m;1:04.50;31.0;r"));
        templateList.add(new LapData("3;1200m;1:35.59;31.0;l"));
        templateList.add(new LapData("4;1600m;2:06.07;30.4;r"));
        templateList.add(new LapData("5;2000m;2:36.49;30.4;l"));
        templateList.add(new LapData("6;2400m;3:07.04;30.5;r"));
        templateList.add(new LapData("7;2800m;3:37.42;30.3;l"));
        templateList.add(new LapData("8;3200m;4:08.26;30.8;r"));
        templateList.add(new LapData("9;3600m;4:38.96;30.7;l"));
        templateList.add(new LapData("10;4000m;5:09.72;30.7;r"));
        templateList.add(new LapData("11;4400m;5:40.51;30.7;l"));
        templateList.add(new LapData("12;4800m;6:11.35;30.8;r"));
        templateList.add(new LapData("13;5200m;6:42.37;31.0;l"));
        templateList.add(new LapData("14;5600m;7:13.25;30.8;r"));
        templateList.add(new LapData("15;6000m;7:44.31;31.0;l"));
        templateList.add(new LapData("16;6400m;8:15.03;30.7;r"));
        templateList.add(new LapData("17;6800m;8:44.95;29.9;l"));
        templateList.add(new LapData("18;7200m;9:15.60;30.6;r"));
        templateList.add(new LapData("19;7600m;9:46.22;30.6;l"));
        templateList.add(new LapData("20;8000m;10:17.10;30.8;r"));
        templateList.add(new LapData("21;8400m;10:48.18;31.0;l"));
        templateList.add(new LapData("22;8800m;11:19.39;31.2;r"));
        templateList.add(new LapData("23;9200m;11:50.61;31.2;l"));
        templateList.add(new LapData("24;9600m;12:22.26;31.6;r"));
        templateList.add(new LapData("25;10000m;12:54.50;32.2;l"));


        final Schedule schedule = new Schedule(this.templateList, null);

        setContentView(R.layout.scheme_entry);
        ListView listview = (ListView) findViewById(R.id.list);
        SchemeEntryAdapter adapter = new SchemeEntryAdapter(SchemeEntryActivity.this, android.R.layout.simple_list_item_1, templateList);
//        adapter.setNotifyOnChange(true);
        listview.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    EditText schemeName = (EditText) findViewById(R.id.schemeName);
                    schedule.setName(schemeName.getText().toString());
                    schedule.writeToJSON();
                    startActivity(new Intent(SchemeEntryActivity.this, ScheduleListActivity.class));
            }
        });

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

}
