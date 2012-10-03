package com.example;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created with IntelliJ IDEA.
 * User: jurrestender
 * Date: 9/21/12
 * Time: 11:23 AM
 */
public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private Context context;

    public ScheduleAdapter(Context context, int textViewResourceId, ArrayList<Schedule> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());

            row = inflater.inflate(R.layout.list_item_with_disclosure, null);

            ImageView disclosure = (ImageView) row.findViewById(R.id.disclosure);
            disclosure.setImageResource(R.drawable.disclosure);
        }

        TextView label = (TextView) row.findViewById(R.id.term);
        if (this.getItem(position).equals(((SpeedSkatingApplication) context.getApplicationContext()).getSchedule())) {
            label.setText(this.getItem(position).getName() + " (selected)");
        } else {
            label.setText(this.getItem(position).getName());
        }


        row.setOnClickListener((View.OnClickListener) this.getContext());
        row.setTag(position);

        return row;
    }
}
