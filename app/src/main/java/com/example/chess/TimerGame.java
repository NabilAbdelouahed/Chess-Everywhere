package com.example.chess;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class TimerGame extends AppCompatActivity {
    private Player[] players = new Player[2];
    public boolean isFirstClick = true;
    private Board board = new Board();
    private Piece swapPiece;
    private Tile swapTile;
    private ImageButton[][] buttons = new ImageButton[8][8];
    private Player currentTurn;
    private GameStatus status;
    private ChessTimer timerPlayer1;
    private ChessTimer timerPlayer2;
    private long extraTime = 1000;
    public int time = -1;
    private TextView timerTextViewPlayer1;
    private TextView timerTextViewPlayer2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_game);
        Intent intent = getIntent();
        time = intent.getIntExtra("time",-1);
        boolean addTime = intent.getBooleanExtra("addTime",false);
        board.resetBoard();
        mapTilesToButtons();



        Player p1 = new Player(true);
        Player p2 = new Player(false);
        players[0] = p1;
        players[1] = p2;
        currentTurn = p1;

        if (time != -1 ) {
            timerTextViewPlayer1 = findViewById(R.id.timer1);
            timerTextViewPlayer2 = findViewById(R.id.timer2);
            timerPlayer1 = new ChessTimer(time * 60 * 1000, addTime);
            timerPlayer2 = new ChessTimer(time * 60 * 1000, addTime);
            updateCountDownText(timerTextViewPlayer1, time * 60 * 1000);
            updateCountDownText(timerTextViewPlayer2, time * 60 * 1000);

            timerPlayer1.start(timerTextViewPlayer1);
        }


    }

    private void mapTilesToButtons() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                String buttonID = "button" + x + y;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[x][y] = findViewById(resID);

                if (board.getBox(x, y).getPiece() != null) {
                    String drawableName = board.getBox(x, y).getPiece().getDrawable(board.getBox(x, y).getPiece().isWhite());
                    int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    buttons[x][y].setImageResource(drawableId);
                }

                // Set a tag to each button
                buttons[x][y].setTag(board.getBox(x, y));

                // Set a click listener to each button
                buttons[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tile tile = (Tile) v.getTag(); // Retrieve the associated tile
                        // Handle the tile click action here
                        onTileClicked(tile);
                    }
                });
            }
        }
    }

    private void onTileClicked(Tile tile) {

        if (isFirstClick && tile.getPiece() != null) {
            swapTile = tile;
            swapPiece = tile.getPiece();
            isFirstClick = false;

            }
        else if (isFirstClick == false ){
            if (swapPiece.canMove(board,swapTile, tile) && currentTurn.isWhiteSide() == swapPiece.isWhite()){
                tile.setPiece(swapPiece);
                swapTile.setPiece(null);
                update_board();
                switchTurn(time);
            }
            isFirstClick = true;
            }

        }

        private void update_board(){
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    String buttonID = "button" + x + y;
                    int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    buttons[x][y] = findViewById(resID);

                    if (board.getBox(x, y).getPiece() != null) {
                        String drawableName = board.getBox(x, y).getPiece().getDrawable(board.getBox(x, y).getPiece().isWhite());
                        int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                        buttons[x][y].setImageResource(drawableId);
                    }
                    else {
                        removeButtonImage(buttonID);
                    }
                }
            }
        }

    public void removeButtonImage(String buttonIdString) {
        int resID = getResources().getIdentifier(buttonIdString, "id", getPackageName());
        ImageButton button = findViewById(resID);
        if (button != null) {
            button.setImageResource(0); // Removes the image
        }
    }

    private void updateCountDownText(TextView textView, long timeInMillis) {
        int minutes = (int) (timeInMillis / 1000) / 60;
        int seconds = (int) (timeInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textView.setText(timeFormatted);
    }

    private void switchTurn(int time) {
        if (currentTurn == players[0]) {
            currentTurn = players[1];
            if (time != -1) {
                if (timerPlayer1.addT) {
                    timerPlayer1.addTime(extraTime, timerTextViewPlayer1);
                }
                timerPlayer1.pause();
                timerPlayer2.start(timerTextViewPlayer2);
            }
        }
        else {
            currentTurn = players[0];
            if (time != -1) {
                if (timerPlayer2.addT) {
                    timerPlayer2.addTime(extraTime, timerTextViewPlayer2);
                }
                timerPlayer2.pause();
                timerPlayer1.start(timerTextViewPlayer1);
            }
        }
    }
}


