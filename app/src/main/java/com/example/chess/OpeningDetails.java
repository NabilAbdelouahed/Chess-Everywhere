package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OpeningDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_details);
        TextView content = findViewById(R.id.content);
        String opDetails = getIntent().getStringExtra("opDetails");
        content.setText(opDetails);
    }
}