package com.example;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/29/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedMenu {

    public static boolean onCreateOptionsMenu(Menu menu, Activity activity) {
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public static boolean onOptionsItemSelected(MenuItem item, Activity activity) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_schema:
                activity.startActivity(new Intent(activity, ScheduleListActivity.class));
                return true;
            case R.id.menu_add:
                activity.startActivity(new Intent(activity, SchemeEntryActivity.class));
                return true;
            case R.id.menu_home:
                activity.startActivity(new Intent(activity, LapDataOverviewActivity.class));
                return true;
            default:
                return activity.onOptionsItemSelected(item);
        }
    }
}
