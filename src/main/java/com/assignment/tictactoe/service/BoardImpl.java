package com.assignment.tictactoe.service;

public class BoardImpl implements Board {
    private Piece[][] pieces = new Piece[3][3];   // This array is to loop through the board

    public BoardImpl() {
        initializeBoard();   // Set all cells to empty
    }

    public Piece[][] getPieces() {
        return pieces;   // Get the current statue of the board
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pieces[i][j] = Piece.EMPTY;   // This loops through every row and column and sets every cell to Empty
            }
        }
    }

    @Override
    public boolean isLegalMove(int row, int col) {
        return pieces[row][col] == Piece.EMPTY;   // This will return if the cell is Empty
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {
        pieces[row][col] = piece;   // Place X in the specified cell
    }

    @Override
    public Winner checkWinner() {   // From this method I checked if there is 3 X or O in line if there is I returned a winner
        for (int i = 0; i < 3; i++) {
            if (pieces[i][0] == pieces[i][1] && pieces[i][0] == pieces[i][2] && pieces[i][0] != Piece.EMPTY) {
                return new Winner(pieces[i][0], i, 0, i, 1, i, 2);
            }
            if (pieces[0][i] == pieces[1][i] && pieces[0][i] == pieces[2][i] && pieces[0][i] != Piece.EMPTY) {
                return new Winner(pieces[0][i], 0, i, 1, i, 2, i);
            }
        }
        if (pieces[0][0] == pieces[1][1] && pieces[0][0] == pieces[2][2] && pieces[0][0] != Piece.EMPTY) {
            return new Winner(pieces[0][0], 0, 0, 1, 1, 2, 2);
        }
        if (pieces[0][2] == pieces[1][1] && pieces[0][2] == pieces[2][0] && pieces[0][2] != Piece.EMPTY) {
            return new Winner(pieces[0][2], 0, 2, 1, 1, 2, 0);
        }
        return null;   //   If no winner found I returned null
    }

    @Override
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(pieces[i][j] + " ");   // This will print the state if every cell in the terminal
            }
            System.out.println();
        }
    }

    public boolean isBoardFull() {   // This method is to verify that the board is full
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return false;   // If it not full returned false
                }
            }
        }
        return true;   // If it is full returned true
    }
}
