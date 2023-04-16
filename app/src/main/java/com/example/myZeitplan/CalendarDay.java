package com.example.myZeitplan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Date;
import java.util.Locale;

public class CalendarDay {
    private long dateInMillis;
    private String note;
    private int dayOfMonth;
    private String dayOfWeekString;

    public CalendarDay(long dateInMillis) {
        this.dateInMillis = dateInMillis;
        this.note = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.dayOfWeekString = new SimpleDateFormat("EEEE", Locale.getDefault()).format(dateInMillis);
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getDayOfWeekString() {
        return dayOfWeekString;
    }
}

