package com.example.trailmakingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

public class StopWatch extends AppCompatActivity {

    private long start;
    private long end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
    }

    public void startStopwatch(View view) {
        start = SystemClock.elapsedRealtime();
    }

    public void stopStopwatch(View view) {
        end = SystemClock.elapsedRealtime();
        int total = (int) (end - start);

        EditText edtTime = findViewById(R.id.Time);

        edtTime.setText("Elapsed Time: " + String.valueOf(total) + " milliseconds");
    }
}