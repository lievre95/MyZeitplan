package com.example.myZeitplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        // Find the TextView in the layout and set its text to the current month
        TextView monthTextView = findViewById(R.id.monthTextView);
        String currentMonth = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        monthTextView.setText(currentMonth);
    }
}