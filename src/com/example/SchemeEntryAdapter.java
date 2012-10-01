package com.example;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SchemeEntryAdapter extends ArrayAdapter<LapData> {
    private final Context context;
    private SchemeEntryActivity activity;
    private ArrayList<LapData> items;

    public SchemeEntryAdapter(Context context, int textViewResourceId, ArrayList<LapData> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.activity = (SchemeEntryActivity) context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());

            row = inflater.inflate(R.layout.inputrow, null);
        }

        TextView roundNumber = (TextView) row.findViewById(R.id.roundnumber);
        final EditText lapTime = (EditText) row.findViewById(R.id.laptime);
        TextView totalTime = (TextView) row.findViewById(R.id.totaltime);
        TextView distance = (TextView) row.findViewById(R.id.distance);

        roundNumber.setText(this.getItem(position).getRoundNumber());
        lapTime.setText(this.getItem(position).getLapTime());
        totalTime.setText(this.getItem(position).getTotalTime());
        distance.setText(this.getItem(position).getDistance());

//        lapTime.addTextChangedListener(new CustomTextWatcher(position));

        final int pos = position;

        lapTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                items.get(pos).setLapTime(((EditText) v).getText().toString().trim());
            }
        });

        return row;
    }
}