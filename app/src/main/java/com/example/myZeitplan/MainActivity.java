package com.example.myZeitplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private TextView monthTextView;
    private ArrayList<CalendarDay> calendarDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        monthTextView = findViewById(R.id.monthTextView);

        // Set the current month as the title
        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        monthTextView.setText(currentMonth);

        // Populate the calendarDays array with the days of the current month
        calendarDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int month = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == month) {
            CalendarDay calendarDay = new CalendarDay(calendar.getTime());
            calendarDays.add(calendarDay);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Set the adapter for the GridView
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, calendarDays);
        gridView.setAdapter(calendarAdapter);

        calendarAdapter.setOnDayClickListener(new CalendarAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(CalendarDay calendarDay) {
                // Handle day click event here
                Toast.makeText(MainActivity.this, "Clicked day " + calendarDay.getDayOfMonth(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
