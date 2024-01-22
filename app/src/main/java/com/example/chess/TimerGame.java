package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class TimerGame extends AppCompatActivity {
    private Player[] players = new Player[2];
    public boolean isFirstClick = true;
    private Board board = new Board();
    private Piece swapPiece;
    private Tile swapTile;
    private ImageButton[][] buttons = new ImageButton[8][8];
    private Player currentTurn;
    private GameStatus status;
    private List<Move> movesPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_game);
        Intent intent = getIntent();
        int time = intent.getIntExtra("time",0);
        boolean addTime = intent.getBooleanExtra("addTime",false);
        board.resetBoard();
        mapTilesToButtons();



//        Player p1 = new Player(true);
//        Player p2 = new Player(false);
//        players[0] = p1;
//        players[1] = p2;
//        currentTurn = p1;
//
//        movesPlayed.clear();
//
//
//
//
//
//
//
//
//
//
//        // set the current turn to the other player
//        if (this.currentTurn == players[0]) {
//            this.currentTurn = players[1];
//        }
//        else {
//            this.currentTurn = players[0];
//        }
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
            if (swapPiece.canMove(board,swapTile, tile)){
                tile.setPiece(swapPiece);
                swapTile.setPiece(null);
                update_board();
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
}


