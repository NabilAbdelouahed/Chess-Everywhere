package com.example.chess;

import android.util.Log;

public class King extends Piece {
    private Boolean kingMoved = false;

    public King(boolean white) {
        super(white);
    }

    public boolean kingMoved() {
        return this.kingMoved;
    }

    public void setKingMoved() {
        this.kingMoved = true;
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end)
    {
        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if ((x<=1)&&(y<=1)) {
            return (!(isKingChecked(board,end,(King)start.getPiece())));
        }
        else {return isCastlingMove(board,start,end);}

    }

    public boolean isCastlingMove(Board board, Tile start, Tile end)
    {
        if (isKingChecked(board, start, (King)start.getPiece()) == false) {
            if (this.kingMoved == false) {
                if (this.isWhite() && end.getX() == 0 && end.getY() == 2 && board.getBox(0, 0).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(0, 0).getPiece()).rookMoved() == false) {
                        for (int i = 1; i < 4; i++) {
                            if (board.getBox(0, i).getPiece() != null) {return false;}
                            if (isKingChecked(board,board.getBox(0, i),(King)start.getPiece() )){return false;}
                        }
                        ((Rook) board.getBox(0, 0).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        return true;
                    }
                } else if (this.isWhite() && end.getX() == 0 && end.getY() == 6 && board.getBox(0, 7).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(0, 7).getPiece()).rookMoved() == false) {
                        for (int i = 5; i < 7; i++) {
                            if (board.getBox(0, i).getPiece() != null) {return false;}
                            if (isKingChecked(board,board.getBox(0, i),(King)start.getPiece() )){return false;}
                        }
                        ((Rook) board.getBox(0, 7).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        return true;
                    }
                } else if (this.isWhite() == false && end.getX() == 7 && end.getY() == 2 && board.getBox(7, 0).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(7, 0).getPiece()).rookMoved() == false) {
                        for (int i = 1; i < 4; i++) {
                            if (board.getBox(7, i).getPiece() != null) {return false;}
                            if (isKingChecked(board,board.getBox(7, i),(King)start.getPiece() )){return false;}
                        }
                        ((Rook) board.getBox(7, 0).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        return true;
                    }
                } else if (this.isWhite() == false  && end.getX() == 7 && end.getY() == 6 && board.getBox(7, 7).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(7, 7).getPiece()).rookMoved() == false) {
                        for (int i = 5; i < 7; i++) {
                            if (board.getBox(7, i).getPiece() != null) {return false;}
                            if (isKingChecked(board,board.getBox(7, i),(King)start.getPiece() )){return false;}
                        }
                        ((Rook) board.getBox(7, 7).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_king_rotated");}
        return ("white_king");
    }

    public boolean isKingChecked(Board board, Tile kingTile, King king){
        for (int row = 0; row<8; row++){
            for(int column = 0; column<8;column++){
                if (board.getBox(row,column).getPiece() != null) {
                    if (!(board.getBox(row,column).getPiece() instanceof King)) {
                        if (board.getBox(row, column).getPiece().isWhite() != king.isWhite() && board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), kingTile)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean kingsFaceOffMove(Board board,Tile start, Tile end){
        return(true);
    }
}

