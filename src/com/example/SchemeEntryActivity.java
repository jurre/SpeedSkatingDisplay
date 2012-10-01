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

        templateList.add(new LapData("1;400m;35.15;35.1"));
        templateList.add(new LapData("2;800m;1:05.79;30.6"));
        templateList.add(new LapData("3;1200m;1:36.73;30.9"));
        templateList.add(new LapData("4;1600m;2:07.95;31.2"));
        templateList.add(new LapData("5;2000m;2:39.13;31.1"));
        templateList.add(new LapData("6;2400m;3:10.28;31.1"));
        templateList.add(new LapData("7;2800m;3:41.41;31.1"));
        templateList.add(new LapData("8;3200m;4:12.62;31.2"));
        templateList.add(new LapData("9;3600m;4:43.98;31.3"));
        templateList.add(new LapData("10;4000m;5:15.25;31.2"));
        templateList.add(new LapData("11;4400m;5:46.28;31.0"));
        templateList.add(new LapData("12;4800m;6:17.44;31.1"));
        templateList.add(new LapData("13;5200m;6:48.54;31.1"));
        templateList.add(new LapData("14;5600m;7:19.73;31.1"));
        templateList.add(new LapData("15;6000m;7:50.73;31.0"));
        templateList.add(new LapData("16;6400m;8:21.80;31.0"));
        templateList.add(new LapData("17;6800m;8:53.12;31.3"));
        templateList.add(new LapData("18;7200m;9:24.50;31.3"));
        templateList.add(new LapData("19;7600m;9:55.68;31.1"));
        templateList.add(new LapData("20;8000m;10:27.00;31.3"));
        templateList.add(new LapData("21;8400m;10:58.29;31.2"));
        templateList.add(new LapData("22;8800m;11:29.42;31.1"));
        templateList.add(new LapData("23;9200m;12:00.60;31.1"));
        templateList.add(new LapData("24;9600m;12:31.27;30.6"));
        templateList.add(new LapData("25;10000m;13:02.07;30.8"));


        final Schedule schedule = new Schedule(this.templateList, null);

        setContentView(R.layout.scheme_entry);
        ListView listview = (ListView) findViewById(R.id.list);
        SchemeEntryAdapter adapter = new SchemeEntryAdapter(SchemeEntryActivity.this, android.R.layout.simple_list_item_1, templateList);
//        adapter.setNotifyOnChange(true);
        listview.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.button);
        final Context context = this;
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
