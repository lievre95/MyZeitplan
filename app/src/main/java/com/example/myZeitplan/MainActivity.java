package com.example.myZeitplan;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myZeitplan.AddNoteDialogFragment;

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
    private NoteDatabaseHelper noteDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabaseHelper = new NoteDatabaseHelper(this);

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
            CalendarDay calendarDay = new CalendarDay(calendar.getTimeInMillis());
            calendarDays.add(calendarDay);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Load the notes from the database and add them to the calendar days
        ArrayList<Note> notes = (ArrayList<Note>) noteDatabaseHelper.getAllNotes();
        for (Note note : notes) {
            CalendarDay calendarDay = new CalendarDay(note.getTimestamp());
            calendarDay.setNote(note.getTitle());
            calendarDays.set(calendarDay.getDayOfMonth() - 1, calendarDay);
        }

        // Set the adapter for the GridView
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, calendarDays);
        gridView.setAdapter(calendarAdapter);

        calendarAdapter.setOnDayClickListener(new CalendarAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(CalendarDay calendarDay) {
                // Handle day click event here
                showAddNoteDialog(calendarDay);
            }
        });
    }

    private void showAddNoteDialog(CalendarDay calendarDay) {
        AddNoteDialogFragment dialogFragment = AddNoteDialogFragment.newInstance(calendarDay);
        dialogFragment.setOnNoteAddedListener(new AddNoteDialogFragment.OnNoteAddedListener() {
            @Override
            public void onNoteAdded(Note note) {
                noteDatabaseHelper.addNote(note);
                updateCalendarView();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "AddNoteDialogFragment");
    }

    private void updateCalendarView() {
        calendarDays.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int month = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == month) {
            CalendarDay calendarDay = new CalendarDay(calendar.getTimeInMillis());
            calendarDays.add(calendarDay);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        ArrayList<Note> notes = (ArrayList<Note>) noteDatabaseHelper.getAllNotes();
        for (Note note : notes) {
            CalendarDay calendarDay = new CalendarDay(note.getDateInMillis());
            calendarDay.setNote(note.getTitle());
            calendarDays.set(calendarDay.getDayOfMonth() - 1, calendarDay);
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(this, calendarDays);
        gridView.setAdapter(calendarAdapter);
    }
}
