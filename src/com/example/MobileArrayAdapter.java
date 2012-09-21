package com.example;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    Schedule schedule;

    public MobileArrayAdapter(Context context, Schedule schedule, String[] roundnumbers) {
        super(context, R.layout.inputrow, roundnumbers);
        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.inputrow, parent, false);
        TextView roundnumber = (TextView) rowView.findViewById(R.id.roundnumber);
        EditText laptime = (EditText) rowView.findViewById(R.id.laptime);
        TextView totaltime = (TextView) rowView.findViewById(R.id.totaltime);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);

        roundnumber.setText(schedule.getRound(position).getRoundnumber());
        laptime.setText(schedule.getRound(position).getLapTime());
        totaltime.setText(schedule.getRound(position).getTotalTime());
        distance.setText(schedule.getRound(position).getDistance());

        return rowView;
    }
}