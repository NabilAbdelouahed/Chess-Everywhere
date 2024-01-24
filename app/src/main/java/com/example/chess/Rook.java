package com.example.chess;

public class Rook extends Piece{
    public boolean rookMoved = false;
    public Rook(Boolean white)
    {
        super(white);
    }

    public boolean rookMoved() {
        return this.rookMoved;
    }

    public void setRookMoved() {
        this.rookMoved = true;
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
        if (!((x==0 && y >0) || (y == 0 && x>0))) {
            return false;
        }
        int dirX = end.getX() > start.getX() ? 1 : -1;
        int dirY = end.getY() > start.getY() ? 1 : -1;
        if (x==0){dirX = 0;}
        if (y==0){dirY = 0;}
        for (int i = 1; i < x + y ; i++){
            if(board.getBox(start.getX() + i*dirX, start.getY() + i*dirY).getPiece() != null){
                return false;
            }
        }
        return true;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_rook_rotated");}
        return ("white_rook");
    }
}
