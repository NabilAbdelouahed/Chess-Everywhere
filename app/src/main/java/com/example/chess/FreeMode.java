package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FreeMode extends AppCompatActivity {

    public boolean isFirstClick = true;

    public Drawable buttonDrawable;
    public ImageButton clickedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode);

    }
    public void show_moves(View view) {
        clickedButton = (ImageButton) view;
        if (isFirstClick) {
            buttonDrawable = clickedButton.getDrawable();
            if (buttonDrawable != null) {
                isFirstClick = false;
            }
        } else {
            if (buttonDrawable != null) {
                clickedButton.setImageDrawable(buttonDrawable);
            }
            isFirstClick = true;
        }
    }
}