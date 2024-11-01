package com.assignment.tictactoe.service;

public class AiPlayer extends Player {

    public AiPlayer(BoardImpl board) {
        super(board); // Accessing the constructor of the superclass to initialize the board
    }

    @Override
    public void move(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.O); // Place O on the board if the move is legal
        }
    }

    public void findBestMove() {
        Move bestMove = findBestMoveUsingMinimax();   // Calculate the best move
        if (bestMove != null) {
            move(bestMove.row, bestMove.col);    // Do the best move
        }
    }

    private Move findBestMoveUsingMinimax() {
        int bestValue = Integer.MIN_VALUE;   // this stores the highest value for a move
        Move bestMove = null;
        Piece[][] pieces = board.getPieces();

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    pieces[i][j] = Piece.O; // If the cell is empty simulate the move
                    int moveValue = minimax(pieces, 0, false);   // check the move with minimax
                    pieces[i][j] = Piece.EMPTY; // Undo the move

                    if (moveValue > bestValue) {
                        bestMove = new Move(i, j); // Store the best move
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove; // Return the best move found
    }

    private int minimax(Piece[][] pieces, int depth, boolean isMaximizing) {
        Winner winner = board.checkWinner();   // this is to check if there is a winner
        if (winner != null) {
            return evaluateScore(winner, depth);   // if there is a winner calculate the score
        }

        if (board.isBoardFull()) {   // this is for if the board is full there is no winner
            return 0; // So I returned it as a draw
        }

        return isMaximizing ? maximizingPlayer(pieces, depth) : minimizingPlayer(pieces, depth);
    }

    private int maximizingPlayer(Piece[][] pieces, int depth) {   // Player with "O"
        int bestValue = Integer.MIN_VALUE;   // put best value to minimum integer
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    pieces[i][j] = Piece.O; // If cell is empty simulate O's move
                    bestValue = Math.max(bestValue, minimax(pieces, depth + 1, false));
                    pieces[i][j] = Piece.EMPTY; // Undo the move
                }
            }
        }
        return bestValue;
    }

    private int minimizingPlayer(Piece[][] pieces, int depth) {   // Player with "X"
        int bestValue = Integer.MAX_VALUE;   // put best value to maximum integer
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    pieces[i][j] = Piece.X; // If cell is empty simulate X's move
                    bestValue = Math.min(bestValue, minimax(pieces, depth + 1, true));
                    pieces[i][j] = Piece.EMPTY; // Undo the move
                }
            }
        }
        return bestValue;
    }

    private int evaluateScore(Winner winner, int depth) {
        if (winner.getWinningPiece() == Piece.O) {   // Check if O won
            return 10 - depth; // Favorable outcome for O
        } else if (winner.getWinningPiece() == Piece.X) {   // Check if X won
            return depth - 10; // Unfavorable outcome for O
        }
        return 0; // Should not reach here, but safe to return 0
    }

    private static class Move {
        int row;
        int col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
