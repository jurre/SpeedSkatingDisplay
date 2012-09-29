package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/21/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchemeEntryActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LapData lapData = new LapData("1;400m;1:08.32;31.2");
        LapData lapData1 = new LapData("2;800m;1:06.22;32.2");
        ArrayList<LapData> list = new ArrayList<LapData>();
        list.add(lapData);
        list.add(lapData1);
        Schedule schedule = new Schedule(list, "test");


        setContentView(R.layout.scheme_entry);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(new MobileArrayAdapter(this, schedule, schedule.getRoundNumbers()));
    }
    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    } */

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
