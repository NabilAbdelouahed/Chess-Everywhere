package com.example.chess;

import android.util.Log;

import java.util.Optional;

public class King extends Piece {
    private Boolean kingMoved = false;
    public boolean checkmate = false;

    public King(boolean white) {
        super(white);
    }

    public boolean kingMoved() {
        return this.kingMoved;
    }

    public void setKingMoved() {
        this.kingMoved = true;
    }
    public boolean kingMated() {
        return this.checkmate;
    }

    public void setKingMated() {
        this.checkmate = true;
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if ((x <= 1) && (y <= 1)) {
            if (end.getPiece() != null) {
                return(!(isKingCheckedCaptureMove(board, start, end, (King) start.getPiece())));
            }
            return (!(isKingChecked(board, end, (King) start.getPiece())));
        } else {
            return isCastlingMove(board, start, end);
        }

    }

    public boolean isCastlingMove(Board board, Tile start, Tile end) {
        if (isKingChecked(board, start, (King) start.getPiece()) == false) {
            if (this.kingMoved == false) {
                if (this.isWhite() && end.getX() == 0 && end.getY() == 2 && board.getBox(0, 0).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(0, 0).getPiece()).rookMoved() == false) {
                        for (int i = 1; i < 4; i++) {
                            if (board.getBox(0, i).getPiece() != null) {
                                return false;
                            }
                            if (isKingChecked(board, board.getBox(0, i), (King) start.getPiece())) {
                                return false;
                            }
                        }
                        ((Rook) board.getBox(0, 0).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        board.getBox(0, 3).setPiece(board.getBox(0, 0).getPiece());
                        board.getBox(0, 0).setPiece(null);
                        return true;
                    }
                } else if (this.isWhite() && end.getX() == 0 && end.getY() == 6 && board.getBox(0, 7).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(0, 7).getPiece()).rookMoved() == false) {
                        for (int i = 5; i < 7; i++) {
                            if (board.getBox(0, i).getPiece() != null) {
                                return false;
                            }
                            if (isKingChecked(board, board.getBox(0, i), (King) start.getPiece())) {
                                return false;
                            }
                        }
                        ((Rook) board.getBox(0, 7).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        board.getBox(0, 5).setPiece(board.getBox(0, 7).getPiece());
                        board.getBox(0, 7).setPiece(null);
                        return true;
                    }
                } else if (this.isWhite() == false && end.getX() == 7 && end.getY() == 2 && board.getBox(7, 0).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(7, 0).getPiece()).rookMoved() == false) {
                        for (int i = 1; i < 4; i++) {
                            if (board.getBox(7, i).getPiece() != null) {
                                return false;
                            }
                            if (isKingChecked(board, board.getBox(7, i), (King) start.getPiece())) {
                                return false;
                            }
                        }
                        ((Rook) board.getBox(7, 0).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        board.getBox(7, 3).setPiece(board.getBox(7, 0).getPiece());
                        board.getBox(7, 0).setPiece(null);
                        return true;
                    }
                } else if (this.isWhite() == false && end.getX() == 7 && end.getY() == 6 && board.getBox(7, 7).getPiece() instanceof Rook) {
                    if (((Rook) board.getBox(7, 7).getPiece()).rookMoved() == false) {
                        for (int i = 5; i < 7; i++) {
                            if (board.getBox(7, i).getPiece() != null) {
                                return false;
                            }
                            if (isKingChecked(board, board.getBox(7, i), (King) start.getPiece())) {
                                return false;
                            }
                        }
                        ((Rook) board.getBox(7, 7).getPiece()).setRookMoved();
                        ((King) start.getPiece()).setKingMoved();
                        board.getBox(7, 5).setPiece(board.getBox(7, 7).getPiece());
                        board.getBox(7, 7).setPiece(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getDrawable(boolean white) {
        if (white == false) {
            return ("black_king_rotated");
        }
        return ("white_king");
    }

    public boolean isKingChecked(Board board, Tile kingTile, King king) {
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (board.getBox(row, column).getPiece() != null) {
                        if (!(board.getBox(row, column).getPiece() instanceof King || board.getBox(row, column).getPiece() instanceof Pawn)) {
                            if (board.getBox(row, column).getPiece().isWhite() != king.isWhite() && board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), kingTile)) {
                                return true;
                            }
                        } else if (board.getBox(row, column).getPiece() instanceof King) {
                            if (board.getBox(row, column).getPiece().isWhite() != king.isWhite() && kingsFaceOffMove(board, kingTile, king, Optional.of(board.getBox(row, column)))) {
                                return true;
                            }
                        } else if (board.getBox(row, column).getPiece() instanceof Pawn) {
                            if (board.getBox(row, column).getPiece().isWhite() != king.isWhite()) {
                                if (board.getBox(row, column).getPiece().isWhite()) {
                                    if (kingTile.getX() == row + 1 && (kingTile.getY() == column + 1 || kingTile.getY() == column - 1)) {
                                        return true;
                                    }
                                }
                                else {
                                    if (kingTile.getX() == row - 1 && (kingTile.getY() == column + 1 || kingTile.getY() == column - 1)) {
                                        return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        return false;
    }



    public boolean kingsFaceOffMove(Board board, Tile end, King firstKing, Optional<Tile> secondKingTile) {
        if (secondKingTile.isPresent()) {
            // Get the position of the second king
            Tile secondKingPosition = secondKingTile.get();

            // Check if the kings are next to each other
            int x = Math.abs(end.getX() - secondKingPosition.getX());
            int y = Math.abs(end.getY() - secondKingPosition.getY());

            // If the absolute difference between the x and y coordinates of the two kings is 1 or less, they are next to each other
            return x <= 1 && y <= 1;
        }
        else {
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (board.getBox(row, column).getPiece() instanceof King && board.getBox(row, column).getPiece().isWhite() != firstKing.isWhite()) {
                        // Get the position of the second king
                        Tile secondKingPosition = board.getBox(row, column);

                        // Check if the kings are next to each other
                        int x = Math.abs(end.getX() - secondKingPosition.getX());
                        int y = Math.abs(end.getY() - secondKingPosition.getY());

                        // If the absolute difference between the x and y coordinates of the two kings is 1 or less, they are next to each other
                        return x <= 1 && y <= 1;
                    }
                }
            }
        }
        return false;
    }

    public boolean isKingMated(Board board, Tile kingTile, King king) {
        if (isKingChecked(board, kingTile, king)) {
            for (int row = kingTile.getX() - 1; row <= kingTile.getX() + 1; row++) {
                for (int column = kingTile.getY() - 1; column <= kingTile.getY() + 1; column++) {
                    if (row >= 0 && row < 8 && column >= 0 && column < 8) {
                        if (king.canMove(board, kingTile, board.getBox(row, column)) ) {
                            return false;
                        }
                    }
                }
            }
            Piece protectingPiece;
            Piece attackedPiece;
            for(int row = 0; row < 8; row++){
                for(int column = 0; column < 8; column++){
                    if(board.getBox(row, column).getPiece() != null){
                        if(board.getBox(row, column).getPiece().isWhite() == king.isWhite()){
                            for(int row2 = 0; row2 < 8; row2++){
                                for(int column2 = 0; column2 < 8; column2++){
                                    if (board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), board.getBox(row2, column2))) {
                                        protectingPiece = board.getBox(row, column).getPiece();
                                        attackedPiece = board.getBox(row2, column2).getPiece();
                                        board.getBox(row2, column2).setPiece(protectingPiece);
                                        board.getBox(row, column).setPiece(null);
                                        if (!isKingChecked(board, kingTile, king)) {
                                            board.getBox(row, column).setPiece(protectingPiece);
                                            board.getBox(row2, column2).setPiece(attackedPiece);
                                            return false;
                                        }
                                        else {
                                            board.getBox(row, column).setPiece(protectingPiece);
                                            board.getBox(row2, column2).setPiece(attackedPiece);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean isKingCheckedCaptureMove(Board board, Tile start, Tile end, King king) {
        Piece capturedPiece = end.getPiece();
        end.setPiece(king);
        start.setPiece(null);
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (board.getBox(row, column).getPiece() != null) {
                    if (!(board.getBox(row, column).getPiece() instanceof King || board.getBox(row, column).getPiece() instanceof Pawn)) {
                        if (board.getBox(row, column).getPiece().isWhite() != king.isWhite() && board.getBox(row, column).getPiece().canMove(board, board.getBox(row, column), end)) {
                            start.setPiece(king);
                            end.setPiece(capturedPiece);
                            return true;
                        }
                    } else if (board.getBox(row, column).getPiece() instanceof King) {
                        if (board.getBox(row, column).getPiece().isWhite() != king.isWhite() && kingsFaceOffMove(board, end, king, Optional.of(board.getBox(row, column)))) {
                            start.setPiece(king);
                            end.setPiece(capturedPiece);
                            return true;
                        }
                    } else if (board.getBox(row, column).getPiece() instanceof Pawn) {
                        if (board.getBox(row, column).getPiece().isWhite() != king.isWhite()) {
                            if (board.getBox(row, column).getPiece().isWhite()) {
                                if (end.getX() == row + 1 && (end.getY() == column + 1 || end.getY() == column - 1)) {
                                    start.setPiece(king);
                                    end.setPiece(capturedPiece);
                                    return true;
                                } else {
                                    if (end.getX() == row - 1 && (end.getY() == column + 1 || end.getY() == column - 1)) {
                                        start.setPiece(king);
                                        end.setPiece(capturedPiece);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        start.setPiece(king);
        end.setPiece(capturedPiece);
        return false;
    }
}
