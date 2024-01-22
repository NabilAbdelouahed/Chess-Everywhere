package com.example.chess;

public class Bishop extends Piece {

    public Bishop(Boolean white)
    {
        super(white);
    }
    @Override
    public boolean canMove(Board board, Tile start,
                           Tile end)
    {
        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }

        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if (x != y ){
            return(false);
        }
        int dirX = end.getX() > start.getX() ? 1 : -1;
        int dirY = end.getY() > start.getY() ? 1 : -1;
        for (int i = 1; i < x ; i++){
            if(board.getBox(start.getX() + i*dirX, start.getY() + i*dirY).getPiece() != null){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_bishop_rotated");}
        return ("white_bishop");
    }
}
