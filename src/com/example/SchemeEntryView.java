package com.example;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marthyn
 * Date: 9/21/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchemeEntryView extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LapData l1 = new LapData("1;400m;1:08.32;31.2");
        LapData l2 = new LapData("2;800m;1:06.22;32.2");
        ArrayList<LapData> list = new ArrayList<LapData>();
        list.add(l1);
        list.add(l2);
        Schedule s1 =  new Schedule(list, "test");


        setContentView(R.layout.schemeentrylayout);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(new MobileArrayAdapter(this, s1, s1.getRoundNumbers()));


    }
    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    } */
}
