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
    private Drawable tempDrawable;
    private int tilePositionSum;
    private View lastClickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode);
    }
    public void show_moves(View view) {
        String id = getResources().getResourceEntryName(view.getId());
        clickedButton = (ImageButton) view;
        if (isFirstClick && id.charAt(0)=='s') {
            lastClickedButton = view;
            tempDrawable = view.getBackground();
            view.setBackground(getDrawable(R.drawable.white_tile_lighted));
            buttonDrawable = clickedButton.getDrawable();
            if (buttonDrawable != null) {
                isFirstClick = false;
            }
        }
        else if(isFirstClick == false && id.charAt(0)=='b' && move == false) {
            if (buttonDrawable != null) {
                clickedButton.setImageDrawable(buttonDrawable);
            }
            lastClickedButton.setBackground(tempDrawable);
            isFirstClick = true;
        }
        else if(isFirstClick && id.charAt(0)=='b' && ((ImageButton) view).getDrawable() != null){
            buttonDrawable = clickedButton.getDrawable();
            lastClickedButton = view;
            tempDrawable = view.getBackground();
            tilePositionSum = (int) (Integer.valueOf(id.substring(id.length() - 2)) % 10 )+ (Integer.valueOf(id.substring(id.length() - 2)) / 10) ;
            if (tilePositionSum % 2 == 0){
                view.setBackground(getDrawable(R.drawable.blue_tile_lighted));
            }
            else{
                view.setBackground(getDrawable(R.drawable.white_tile_lighted));
            }
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
            lastClickedButton.setBackground(tempDrawable);
            isFirstClick = true;
            move = false;

        }
        else if(!isFirstClick && id.charAt(0)=='s' && getResources().getResourceEntryName(lastClickedButton.getId()).charAt(0) == 's'){
            lastClickedButton.setBackground(getDrawable(R.drawable.white_tile));
            buttonDrawable = clickedButton.getDrawable();
            view.setBackground(getDrawable(R.drawable.white_tile_lighted));
            lastClickedButton =  view;

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