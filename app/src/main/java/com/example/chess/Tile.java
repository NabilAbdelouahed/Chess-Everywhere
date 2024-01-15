package com.example.chess;

public class Tile {

    private Piece piece;
    private int x;
    private int y;

    public Tile(int x, int y, Piece piece){
        this.setPiece(piece);
        this.setX(x);
        this.setY(y);
    }
    public Piece getPiece(){
        return(this.piece);
    }
    public void setPiece(Piece p){
        this.piece = p;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return this.y;
    }
    public int getX() {
        return this.x;
    }
}
