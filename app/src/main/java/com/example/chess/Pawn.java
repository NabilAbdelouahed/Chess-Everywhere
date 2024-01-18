package com.example.chess;

public class Pawn extends Piece{
    public Pawn(Boolean white)
    {
        super(white);
    }
    @Override
    public boolean canMove(Board board, Tile start,
                           Tile end) {
        int x = end.getX() - start.getX();
        int y = end.getY() - start.getY();
        // Movement direction depends on the pawn's color
        int direction = start.getPiece().isWhite() ? 1 : -1;

        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
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
        if (Math.abs(y) == 1 && x == direction ) {
            return true;
        }
        return false;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_pawn_rotated");}
        return ("white_pawn");
    }
}
