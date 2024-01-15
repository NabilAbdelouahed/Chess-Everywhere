package com.example.chess;

public class Move {
    private Player player;
    private Tile start;
    private Tile end;
    private Piece pieceMoved;
    private Piece pieceKilled;
    private boolean castlingMove = false;

    public Move(Player player, Tile start, Tile end)
    {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceMoved = start.getPiece();
    }

    public boolean isCastlingMove()
    {
        return this.castlingMove;
    }

    public void setCastlingMove(boolean castlingMove)
    {
        this.castlingMove = castlingMove;
    }
    public Tile getEnd(){
        return this.end;
    }
    public Tile getStart(){
        return this.start;
    }

    public void setPieceKilled(Piece piece) {
        piece.setKilled(true);
    }
}

