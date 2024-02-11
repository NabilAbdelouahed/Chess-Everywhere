package com.example.chess;

import static java.lang.Math.abs;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.util.Log;

public class Pawn extends Piece{
    public TimerGame currentGame = TimerGame.getCurrentInstance();


    public Pawn(Boolean white) {super(white);
    this.currentGame = currentGame;}
    @Override
    public boolean canMove(Board board, Tile start,
                           Tile end) {
        int x = end.getX() - start.getX();
        int y = end.getY() - start.getY();
        // Movement direction depends on the pawn's color
        int direction = start.getPiece().isWhite() ? 1 : -1;


        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }
        if (y == 0 && x == direction && end.getPiece() == null ) {
            return true;
        }
        if (start.getX()== 1 && this.isWhite() && y == 0 && x == 2 && end.getPiece()== null && board.getBox(start.getX()+1,start.getY()).getPiece() == null ) {
            return true;
        }

        if (start.getX()== 6 && this.isWhite() == false && y == 0 && x == -2 && end.getPiece()== null && board.getBox(start.getX()-1,start.getY()).getPiece() == null) {
            return true;
        }
        // Check for capture move
        if (abs(y) == 1 && x == direction && end.getPiece() != null ) {
            return true;
        }
        if (currentGame.previousPawnMove != null && currentGame.previousPawnMove.getPiece() != null){
            if (currentGame.currentTurn.isWhiteSide() != currentGame.previousPawnMove.getPiece().isWhite()){
                if(currentGame.currentTurn.isWhiteSide() && start.getX() == 4 && end.getX() == currentGame.previousPawnMove.getX()+1 && end.getY() == currentGame.previousPawnMove.getY() && abs(y) == 1){
                    return true;
                }
                if(!currentGame.currentTurn.isWhiteSide() && start.getX() == 3 && end.getX() == currentGame.previousPawnMove.getX()-1 && end.getY() == currentGame.previousPawnMove.getY() && abs(y) == 1){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_pawn_rotated");}
        return ("white_pawn");
    }
}
