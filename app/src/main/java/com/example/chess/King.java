package com.example.chess;

public class King extends Piece {
    private Boolean castlingDone = false;

    public King(boolean white) {
        super(white);
    }

    public boolean isCastlingDone() {
        return this.castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end)
    {
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if ((x<=1)&&(y<=1)) {
            // check if this move will not result in the king
            // being attacked if so return true
            return true;
        }
        return this.isValidCastling(board, start, end);
    }
    private boolean isValidCastling(Board board,
                                    Tile start, Tile end)
    {

        if (this.isCastlingDone()) {
            return false;
        }
        if (isCastlingMove(start, end)){
            if (this.isWhite() && end.getY() < start.getY() && board.getBox(0,0).getPiece() instanceof Rook) {
                for(int i = 1; i < start.getY(); i++ ) {
                    if (board.getBox(0,i).getPiece() != null ){
                        return false;
                    }
                }
            //verifier attaques sur le trajet
            //optional : rook and king first move
            }
            if (this.isWhite() && end.getY() > start.getY() && board.getBox(0,7).getPiece() instanceof Rook) {
                for(int i = 5; i < 7; i++ ) {
                    if (board.getBox(0,i).getPiece() != null ){
                        return false;
                    }
                }
                //verifier attaques sur le trajet
                //optional : rook and king first move
            }
            if (this.isWhite() == false && end.getY() > start.getY() && board.getBox(7,7).getPiece() instanceof Rook) {
                for(int i = 5; i < 7; i++ ) {
                    if (board.getBox(7,i).getPiece() != null ){
                        return false;
                    }
                }
                //verifier attaques sur le trajet
                //optional : rook and king first move
            }
            if (this.isWhite() == false && end.getY() < start.getY() && board.getBox(7,0).getPiece() instanceof Rook) {
                for(int i = 1; i < start.getY(); i++ ) {
                    if (board.getBox(7,i).getPiece() != null ){
                        return false;
                    }
                }
                //verifier attaques sur le trajet
                //optional : rook and king first move
            }
        }
        return false;
        }


    public boolean isCastlingMove(Tile start, Tile end)
    {
        if (this.isWhite() && start.getX()== 0 && start.getY()==4 && end.getX()==0 && end.getY()==2) {
            return true;
        }
        if (this.isWhite() && start.getX()== 0 && start.getY()==4 && end.getX()==0 && end.getY()==6) {
            return true;
        }
        if (this.isWhite() == false && start.getX()== 7 && start.getY()==4 && end.getX()==7 && end.getY()==2) {
            return true;
        }
        if (this.isWhite() == false && start.getX()== 7 && start.getY()==4 && end.getX()==7 && end.getY()==6) {
            return true;
        }
        return false;
    }
    @Override
    public String getDrawable(boolean white){
        if (white==false){return ("black_king_rotated");}
        return ("white_king");
    }
}

