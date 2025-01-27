package com.example.chess;

public abstract class Piece {
    private boolean killed = false;
    private boolean white = false;

    public Piece(boolean white)
    {
        this.setWhite(white);
    }

    public boolean isWhite()
    {
        return this.white;
    }

    public void setWhite(boolean white)
    {
        this.white = white;
    }

    public abstract String getDrawable(boolean white);

    public abstract boolean canMove(Board board,
                                    Tile start, Tile end);
}

