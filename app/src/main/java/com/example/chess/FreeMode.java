package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FreeMode extends AppCompatActivity {

    public boolean isFirstClick = true;
    public String start;
    public boolean move;
    public Drawable buttonDrawable;
    public ImageButton clickedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode);
    }
    public void show_moves(View view) {
        String id = getResources().getResourceEntryName(view.getId());
        clickedButton = (ImageButton) view;
        if (isFirstClick && id.charAt(0)=='s') {
            buttonDrawable = clickedButton.getDrawable();
            if (buttonDrawable != null) {
                isFirstClick = false;
            }
        } else if(isFirstClick == false && id.charAt(0)=='b' && move == false) {
            if (buttonDrawable != null) {
                clickedButton.setImageDrawable(buttonDrawable);
            }
            isFirstClick = true;
        }
        else if(isFirstClick && id.charAt(0)=='b'){
            buttonDrawable = clickedButton.getDrawable();
            if (buttonDrawable != null) {
                isFirstClick = false;
                start = id;
                move = true;
            }
        }
        else if(isFirstClick == false && id.charAt(0)=='b' && move == true) {
            if (buttonDrawable != null) {
                removeButtonImage(start);
                clickedButton.setImageDrawable(buttonDrawable);
            }
            isFirstClick = true;
            move = false;

        }
    }
    public void removeButtonImage(String buttonIdString) {
        int resID = getResources().getIdentifier(buttonIdString, "id", getPackageName());
        ImageButton button = findViewById(resID);
        if (button != null) {
            button.setImageResource(0); // Removes the image
        }
    }

    public void clear_board(View view){
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                String buttonID = "button" + x + y;
                removeButtonImage(buttonID);
            }
        }
    }
}