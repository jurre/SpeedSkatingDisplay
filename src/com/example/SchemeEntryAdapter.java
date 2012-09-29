package com.example;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class SchemeEntryAdapter extends ArrayAdapter<String> {
    private final Context context;
    private SchemeEntryActivity activity;
    private Schedule schedule;

    public SchemeEntryAdapter(Context context, Schedule schedule, String[] roundNumbers) {
        super(context, R.layout.inputrow, roundNumbers);
        this.context = context;
        this.schedule = schedule;
        this.activity = (SchemeEntryActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = layoutInflater.inflate(R.layout.inputrow, parent, false);
        TextView roundNumber = (TextView) rowView.findViewById(R.id.roundnumber);
        final EditText lapTime = (EditText) rowView.findViewById(R.id.laptime);
        TextView totalTime = (TextView) rowView.findViewById(R.id.totaltime);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);

        roundNumber.setText(schedule.getRound(position).getRoundNumber());
        lapTime.setText(schedule.getRound(position).getLapTime());
        totalTime.setText(schedule.getRound(position).getTotalTime());
        distance.setText(schedule.getRound(position).getDistance());

        final int lapDataPosition = position;
        lapTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.v("position = ", String.valueOf(lapDataPosition));
                    Log.v("lapTime = ", lapTime.getText().toString());
                    activity.updateLapTime(lapDataPosition, lapTime.getText().toString());
                }
            }
        });

        return rowView;
    }
}