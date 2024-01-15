package com.example.chess;

public class Board {
    Tile[][] boxes;

    public Board()
    {
        this.resetBoard();
    }

    public Tile getBox(int x, int y)
    {
        return boxes[x][y];
    }


    public void resetBoard()
    {
        // initialize white pieces
        boxes[0][0] = new Tile(0, 0, new Rook(true));
        boxes[0][1] = new Tile(0, 1, new Knight(true));
        boxes[0][2] = new Tile(0, 2, new Bishop(true));
        boxes[0][3] = new Tile(0, 3, new Queen(true));
        boxes[0][4] = new Tile(0, 4, new King(true));
        boxes[0][5] = new Tile(0, 5, new Bishop(true));
        boxes[0][6] = new Tile(0, 6, new Knight(true));
        boxes[0][7] = new Tile(0, 7, new Rook(true));

        boxes[1][0] = new Tile(1, 0, new Pawn(true));
        boxes[1][1] = new Tile(1, 1, new Pawn(true));
        boxes[1][2] = new Tile(1, 2, new Pawn(true));
        boxes[1][3] = new Tile(1, 3, new Pawn(true));
        boxes[1][4] = new Tile(1, 4, new Pawn(true));
        boxes[1][5] = new Tile(1, 5, new Pawn(true));
        boxes[1][6] = new Tile(1, 6, new Pawn(true));
        boxes[1][7] = new Tile(1, 7, new Pawn(true));

        // initialize black pieces
        boxes[7][0] = new Tile(7, 0, new Rook(false));
        boxes[7][1] = new Tile(7, 1, new Knight(false));
        boxes[7][2] = new Tile(7, 2, new Bishop(false));
        boxes[7][3] = new Tile(7, 3, new Queen(false));
        boxes[7][4] = new Tile(7, 4, new King(false));
        boxes[7][5] = new Tile(7, 5, new Bishop(false));
        boxes[7][6] = new Tile(7, 6, new Knight(false));
        boxes[7][7] = new Tile(7, 7, new Rook(false));

        boxes[6][0] = new Tile(6, 0, new Pawn(false));
        boxes[6][1] = new Tile(6, 1, new Pawn(false));
        boxes[6][2] = new Tile(6, 2, new Pawn(false));
        boxes[6][3] = new Tile(6, 3, new Pawn(false));
        boxes[6][4] = new Tile(6, 4, new Pawn(false));
        boxes[6][5] = new Tile(6, 5, new Pawn(false));
        boxes[6][6] = new Tile(6, 6, new Pawn(false));
        boxes[6][7] = new Tile(6, 7, new Pawn(false));

        // initialize remaining boxes without any piece
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boxes[i][j] = new Tile(i,j,null);
            }
        }
    }
}
