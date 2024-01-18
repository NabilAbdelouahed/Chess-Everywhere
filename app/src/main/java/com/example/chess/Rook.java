package com.example.chess;

public class Rook extends Piece{
    public Rook(Boolean white)
    {
        super(white);
    }
    @Override
    public boolean canMove(Board board, Tile start,
                           Tile end)
    {
        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return ((x==0 && y >0) || (y == 0 && x>0));
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_rook_rotated");}
        return ("white_rook");
    }
}
