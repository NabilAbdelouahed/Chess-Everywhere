package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class TimerGame extends AppCompatActivity {
    private Player[] players;
    private Board board = new Board();
    private ImageButton[][] buttons = new ImageButton[8][8];
    private Player currentTurn;
    private GameStatus status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_game);
        Intent intent = getIntent();
        int time = intent.getIntExtra("time",0);
        boolean addTime = intent.getBooleanExtra("addTime",false);
        board.resetBoard();
        mapTilesToButtons();
    }

    private void mapTilesToButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                String buttonID = "button" + x + y;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[x][y] = findViewById(resID);
                if (board.getBox(x,y).getPiece() != null) {
                    String drawableName = board.getBox(x,y).getPiece().getDrawable(board.getBox(x,y).getPiece().isWhite());
                    int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    buttons[x][y].setImageResource(drawableId);

                }
                // Optional: Set a tag or listener to each button
                buttons[x][y].setTag(board.getBox(x, y));
            }
        }
    }
}