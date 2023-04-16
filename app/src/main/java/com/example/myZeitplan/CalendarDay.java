package com.example.myZeitplan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Date;
import java.util.Locale;

public class CalendarDay {
    private Date date;
    private String note;
    private int dayOfMonth;
    private String dayOfWeekString;

    public CalendarDay(Date date) {
        this.date = date;
        this.note = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.dayOfWeekString = new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    public Date getDate() {
        return date;
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

