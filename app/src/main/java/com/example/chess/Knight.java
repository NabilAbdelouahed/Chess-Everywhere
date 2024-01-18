package com.example.chess;

public class Knight extends Piece {
    public Knight(boolean white)
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
        return x * y == 2;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_knight_rotated");}
        return ("white_knight");
    }
}

