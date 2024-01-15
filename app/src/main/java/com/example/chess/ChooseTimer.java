package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseTimer extends AppCompatActivity {

    public int time;
    public boolean addTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_timer);
    }


    public void timer_start(View view) {
        int id = view.getId() ;
        String gameType = getResources().getResourceEntryName(id);
        if (gameType == "bullet_min"){
            time = 1;
            addTime = false;
        }
        else if (gameType == "blitz_min"){
            time = 3;
            addTime = false;

        }
        else if (gameType == "rapid_min"){
            time = 10;
            addTime = false;
        }
        else if (gameType == "bullet_min_sec"){
            time = 1;
            addTime = true;
        }
        else if (gameType == "blitz_min_sec"){
            time = 3;
            addTime = true;

        }
        else if (gameType == "rapid_min_sec"){
            time = 10;
            addTime = true;
        }
        Intent intent = new Intent(ChooseTimer.this, TimerGame.class);
        intent.putExtra("time", time);
        intent.putExtra("addTime", time);
        startActivity(intent);
    }

    public void no_timer_start(View view) {

    }


}