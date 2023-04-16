package com.example.myZeitplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<CalendarDay> calendarDays;

    public CalendarAdapter(Context context, List<CalendarDay> calendarDays) {
        this.context = context;
        this.calendarDays = calendarDays;
    }

    @Override
    public int getCount() {
        return calendarDays.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_day, parent, false);
        }

        TextView dayTextView = convertView.findViewById(R.id.dayTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView noteTextView = convertView.findViewById(R.id.noteTextView);

        CalendarDay calendarDay = calendarDays.get(position);

        dayTextView.setText(calendarDay.getDayOfWeekString());
        dateTextView.setText(String.valueOf(calendarDay.getDayOfMonth()));
        noteTextView.setText(calendarDay.getNote());

        return convertView;
    }
}

