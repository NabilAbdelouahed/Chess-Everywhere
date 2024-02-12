package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void switch_to_play_view(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseTimer.class);
        startActivity(intent);
    }


    public void switch_to_opening_view(View view) {
        Intent intent = new Intent(MainActivity.this, Openings.class);
        startActivity(intent);
    }

    public void switch_to_free_mode_view(View view) {
        Intent intent = new Intent(MainActivity.this, FreeMode.class);
        startActivity(intent);
    }
}