package com.example.chess;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class TimerGame extends AppCompatActivity {
    private Player[] players = new Player[2];
    public boolean isFirstClick = true;
    private Board board = new Board();
    private Piece swapPiece;
    private Tile swapTile;
    private ImageButton[][] buttons = new ImageButton[8][8];
    public Player currentTurn;
    public GameStatus status;
    private ChessTimer timerPlayer1;
    private ChessTimer timerPlayer2;
    private long extraTime = 1000;
    private int[] whiteKingPosition = new int[2];
    private int[] blackKingPosition = new int[2];
    public int time = -1;
    private TextView timerTextViewPlayer1;
    private TextView timerTextViewPlayer2;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;
    private Piece tempPiece;
    private Drawable tempTileColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_game);
        status = GameStatus.ACTIVE;
        whiteKingPosition[0] = 0;
        whiteKingPosition[1] = 4;
        blackKingPosition[0] = 7;
        blackKingPosition[1] = 4;
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
            timerPlayer1 = new ChessTimer(time * 60 * 1000, addTime,this);
            timerPlayer2 = new ChessTimer(time * 60 * 1000, addTime, this);
            updateCountDownText(timerTextViewPlayer1, time * 60 * 1000);
            updateCountDownText(timerTextViewPlayer2, time * 60 * 1000);

            timerPlayer1.start(timerTextViewPlayer1);
            timerTextViewPlayer1.setBackgroundColor(Color.BLACK);

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
                        onTileClicked(tile, board);
                    }
                });
            }
        }
    }

    private void onTileClicked(Tile tile, Board board) {

        if (isFirstClick && tile.getPiece() != null) {
            swapTile = tile;
            swapPiece = tile.getPiece();
            isFirstClick = false;
        }

        else if (isFirstClick == false ){
            if (swapPiece.canMove(board,swapTile, tile) && currentTurn.isWhiteSide() == swapPiece.isWhite()){
                if (isWhiteChecked && currentTurn.isWhiteSide()){
                    if (swapPiece instanceof King) {
                        tempPiece = tile.getPiece();
                        tile.setPiece(swapPiece);
                        swapTile.setPiece(null);
                        if (!(((King) tile.getPiece()).isKingChecked(board, tile, (King) tile.getPiece()))) {
                            isWhiteChecked = false;
                            buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(tempTileColor);
                            whiteKingPosition[0] = tile.getX();
                            whiteKingPosition[1] = tile.getY();
                            update_board();
                            switchTurn(time);
                            if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                                isBlackChecked = true;
                                tempTileColor =  buttons[blackKingPosition[0]][blackKingPosition[1]].getBackground();
                                buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            }
                            else if (isStaleMate(board)){
                                status = GameStatus.STALEMATE_DRAW;
                                showGameEndDialog("Draw by stalemate");
                            }
                        }
                        else{
                            tile.setPiece(tempPiece);
                            swapTile.setPiece(swapPiece);
                        }
                    }
                    else{
                        tempPiece = tile.getPiece();
                        tile.setPiece(swapPiece);
                        swapTile.setPiece(null);
                        if (!(((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()))) {
                            isWhiteChecked = false;
                            buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(tempTileColor);
                            if (swapPiece instanceof Pawn && tile.getX()==7){
                                showPawnPromotionDialog(board,swapTile,tile,swapPiece.isWhite());
                            }
                            update_board();
                            switchTurn(time);
                            if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                                isBlackChecked = true;
                                tempTileColor =  buttons[blackKingPosition[0]][blackKingPosition[1]].getBackground();
                                buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            }
                            else if (isStaleMate(board)){
                                status = GameStatus.STALEMATE_DRAW;
                                showGameEndDialog("Draw by stalemate");
                            }
                        }
                        else{
                            tile.setPiece(tempPiece);
                            swapTile.setPiece(swapPiece);
                        }
                    }
                }
                else if (isBlackChecked && !currentTurn.isWhiteSide()){
                    if (swapPiece instanceof King) {
                        tempPiece = tile.getPiece();
                        tile.setPiece(swapPiece);
                        swapTile.setPiece(null);
                        if (!(((King) tile.getPiece()).isKingChecked(board, tile, (King) tile.getPiece()))) {
                            isBlackChecked = false;
                            buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(tempTileColor);
                            blackKingPosition[0] = tile.getX();
                            blackKingPosition[1] = tile.getY();
                            update_board();
                            switchTurn(time);
                            if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                                isWhiteChecked = true;
                                tempTileColor =  buttons[whiteKingPosition[0]][whiteKingPosition[1]].getBackground();
                                buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            }
                            else if (isStaleMate(board)){
                                status = GameStatus.STALEMATE_DRAW;
                                showGameEndDialog("Draw by stalemate");
                            }
                        }
                        else{
                            tile.setPiece(tempPiece);
                            swapTile.setPiece(swapPiece);
                        }
                    }
                    else{
                        tempPiece = tile.getPiece();
                        tile.setPiece(swapPiece);
                        swapTile.setPiece(null);
                        if (!(((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()))) {
                            isBlackChecked = false;
                            buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(tempTileColor);
                            if (swapPiece instanceof Pawn && tile.getX()==0){
                                showPawnPromotionDialog(board,swapTile,tile,swapPiece.isWhite());
                            }
                            update_board();
                            switchTurn(time);

                            if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                                isWhiteChecked = true;
                                tempTileColor =  buttons[whiteKingPosition[0]][whiteKingPosition[1]].getBackground();
                                buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            }
                            else if (isStaleMate(board)){
                                status = GameStatus.STALEMATE_DRAW;
                                showGameEndDialog("Draw by stalemate");
                            }
                        }
                        else{
                            tile.setPiece(tempPiece);
                            swapTile.setPiece(swapPiece);

                        }
                    }
                }
                else{
                    tempPiece = tile.getPiece();
                    tile.setPiece(swapPiece);
                    swapTile.setPiece(null);
                    if (currentTurn.isWhiteSide() && swapPiece instanceof King) {
                        whiteKingPosition[0] = tile.getX();
                        whiteKingPosition[1] = tile.getY();
                    }
                    if (!currentTurn.isWhiteSide() && swapPiece instanceof King) {
                        blackKingPosition[0] = tile.getX();
                        blackKingPosition[1] = tile.getY();
                    }
                    if (currentTurn.isWhiteSide() && !((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                        if (swapPiece instanceof Pawn && tile.getX()==7 && swapPiece.isWhite()){
                            showPawnPromotionDialog(board,swapTile,tile,swapPiece.isWhite());
                        }
                        update_board();
                        switchTurn(time);

                        if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                            isBlackChecked = true;
                            tempTileColor =  buttons[blackKingPosition[0]][blackKingPosition[1]].getBackground();
                            buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingMated(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                                status = GameStatus.CHECKMATE_WHITE_WIN;
                                showGameEndDialog("White wins by checkmate");
                            }
                        }

                        else if (isStaleMate(board)){
                            status = GameStatus.STALEMATE_DRAW;
                            showGameEndDialog("Draw by stalemate");
                        }
                    }
                    else if (!currentTurn.isWhiteSide() && !((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                        if (swapPiece instanceof Pawn && tile.getX()==0 && !swapPiece.isWhite()){
                            showPawnPromotionDialog(board,swapTile,tile,swapPiece.isWhite());
                        }
                        update_board();
                        switchTurn(time);

                        if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                            isWhiteChecked = true;
                            tempTileColor =  buttons[whiteKingPosition[0]][whiteKingPosition[1]].getBackground();
                            buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                            if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingMated(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                                status = GameStatus.CHECKMATE_BLACK_WIN;
                                showGameEndDialog("Black wins by checkmate");
                            }
                        }
                        else if (isStaleMate(board)){
                            status = GameStatus.STALEMATE_DRAW;
                            showGameEndDialog("Draw by stalemate");
                        }

                    }
                    else{
                        tile.setPiece(tempPiece);
                        swapTile.setPiece(swapPiece);
                        if (currentTurn.isWhiteSide() && swapPiece instanceof King) {
                            whiteKingPosition[0] = swapTile.getX();
                            whiteKingPosition[1] = swapTile.getY();
                        }
                        if (!currentTurn.isWhiteSide() && swapPiece instanceof King) {
                            blackKingPosition[0] = swapTile.getX();
                            blackKingPosition[1] = swapTile.getY();
                        }
                    }
                }
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
                timerTextViewPlayer1.setBackgroundColor(0);
                timerTextViewPlayer2.setBackgroundColor(Color.BLACK);
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
                timerTextViewPlayer2.setBackgroundColor(0);
                timerTextViewPlayer1.setBackgroundColor(Color.BLACK);
            }
        }
    }
    public void resign(View view){
        int resID = view.getId();
        String buttonID = getResources().getResourceEntryName(resID);
        if (buttonID.equals("resignBlack")){
            status = GameStatus.RESIGNATION_WHITE_WIN;
            showGameEndDialogResign("White wins by resignation");
        }
        else{
            status = GameStatus.RESIGNATION_BLACK_WIN;
            showGameEndDialogResign("Black wins by resignation");
        }
    }
    private void showGameEndDialog(String message) {
        timerPlayer1.pause();
        timerPlayer2.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showEndGameDialogTimeUp(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void restartGame(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to restart the game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartActivity();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showGameEndDialogResign(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to resign?");
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerPlayer1.pause();
                timerPlayer2.pause();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(TimerGame.this);
                builder2.setMessage(message);
                builder2.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartActivity();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
            }
        });
        builder1.setNegativeButton("No", null);
        AlertDialog dialog = builder1.create();
        dialog.show();
    }
    public void promotePawn(Board board, Tile start, Tile end, Piece piece){
        end.setPiece(piece);
        start.setPiece(null);
        if (!piece.isWhite()){
            if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                isWhiteChecked = true;
                tempTileColor =  buttons[whiteKingPosition[0]][whiteKingPosition[1]].getBackground();
                buttons[whiteKingPosition[0]][whiteKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingMated(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                    status = GameStatus.CHECKMATE_BLACK_WIN;
                    showGameEndDialog("Black wins by checkmate");
                }
            }
        }
        else{
            if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                isBlackChecked = true;
                tempTileColor =  buttons[blackKingPosition[0]][blackKingPosition[1]].getBackground();
                buttons[blackKingPosition[0]][blackKingPosition[1]].setBackground(getDrawable(R.drawable.red_tile));
                if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingMated(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                    status = GameStatus.CHECKMATE_WHITE_WIN;
                    showGameEndDialog("White wins by checkmate");
                }
            }
        }
        update_board();
    }

    public void showPawnPromotionDialog(Board board, Tile start, Tile end, Boolean white) {
        CharSequence[] items = {"Queen", "Rook", "Bishop", "Knight"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose piece for promotion");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 'which' is the index of the selected item
                switch (which) {
                    case 0: promotePawn(board, start,end,new Queen(white)); break;
                    case 1: promotePawn(board, start,end,new Rook(white)); break;
                    case 2: promotePawn(board, start,end,new Bishop(white)); break;
                    case 3: promotePawn(board, start,end,new Knight(white)); break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isStaleMate(Board board){

        if (currentTurn.isWhiteSide() && !((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (board.getBox(row, column).getPiece() != null && board.getBox(row, column).getPiece().isWhite()) {
                        for (int row1 = 0; row1 < 8; row1++) {
                            for (int column1 = 0; column1 < 8; column1++) {
                                if (board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), board.getBox(row1, column1))) {
                                    Piece tempPieceStale = board.getBox(row, column).getPiece();
                                    Piece tempPieceSwap = board.getBox(row1, column1).getPiece();
                                    board.getBox(row, column).setPiece(null);
                                    board.getBox(row1, column1).setPiece(tempPieceStale);
                                    if (((King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(whiteKingPosition[0], whiteKingPosition[1]), (King) board.getBox(whiteKingPosition[0], whiteKingPosition[1]).getPiece())){
                                        board.getBox(row, column).setPiece(tempPieceStale);
                                        board.getBox(row1, column1).setPiece(tempPieceSwap);
                                    }
                                    else{
                                        board.getBox(row, column).setPiece(tempPieceStale);
                                        board.getBox(row1, column1).setPiece(tempPieceSwap);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (!currentTurn.isWhiteSide() && !((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (board.getBox(row, column).getPiece() != null && !(board.getBox(row, column).getPiece().isWhite())) {
                        for (int row1 = 0; row1 < 8; row1++) {
                            for (int column1 = 0; column1 < 8; column1++) {
                                if (board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), board.getBox(row1, column1))) {
                                    Piece tempPieceStale = board.getBox(row, column).getPiece();
                                    Piece tempPieceSwap = board.getBox(row1, column1).getPiece();
                                    board.getBox(row, column).setPiece(null);
                                    board.getBox(row1, column1).setPiece(tempPieceStale);
                                    if (((King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece()).isKingChecked(board, board.getBox(blackKingPosition[0], blackKingPosition[1]), (King) board.getBox(blackKingPosition[0], blackKingPosition[1]).getPiece())){
                                        board.getBox(row, column).setPiece(tempPieceStale);
                                        board.getBox(row1, column1).setPiece(tempPieceSwap);
                                    }
                                    else{
                                        board.getBox(row, column).setPiece(tempPieceStale);
                                        board.getBox(row1, column1).setPiece(tempPieceSwap);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}


