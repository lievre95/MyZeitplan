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
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabaseHelper = new NoteDatabaseHelper(this);

        gridView = findViewById(R.id.gridView);
        monthTextView = findViewById(R.id.monthTextView);

        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        monthTextView.setText(currentMonth);

        calendarDays = new ArrayList<>();
        populateCalendarDays();

        calendarAdapter = new CalendarAdapter(this, calendarDays);
        gridView.setAdapter(calendarAdapter);

        calendarAdapter.setOnDayClickListener(new CalendarAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(CalendarDay calendarDay) {
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
                updateCalendarDay(note);
                calendarAdapter.notifyDataSetChanged();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "AddNoteDialogFragment");
    }

    private void populateCalendarDays() {
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
            int dayIndex = getDayIndex(note.getDateInMillis());
            if (dayIndex != -1) {
                calendarDays.get(dayIndex).setNote(note.getTitle());
            }
        }
    }

    private void updateCalendarDay(Note note) {
        int dayIndex = getDayIndex(note.getDateInMillis());
        if (dayIndex != -1) {
            calendarDays.get(dayIndex).setNote(note.getTitle());
        }
    }

    private int getDayIndex(long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        return (dayOfMonth >= 0 && dayOfMonth < calendarDays.size()) ? dayOfMonth : -1;
    }
}