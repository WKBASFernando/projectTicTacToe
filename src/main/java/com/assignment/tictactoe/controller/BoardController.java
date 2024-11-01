package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardController implements BoardUI {
    BoardImpl board;
    AiPlayer ai;
    HumanPlayer human;
    private Button[][] buttons = new Button[3][3];

    public BoardController() {
        board = new BoardImpl();
        ai = new AiPlayer(board);
        human = new HumanPlayer(board);
    }


    @FXML
    private ImageView ImageView;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Button id00;

    @FXML
    private Button id01;

    @FXML
    private Button id02;

    @FXML
    private Button id10;

    @FXML
    private Button id11;

    @FXML
    private Button id12;

    @FXML
    private Button id20;

    @FXML
    private Button id21;

    @FXML
    private Button id22;

    @FXML
    void buttonClicked(ActionEvent event) {
        Button button = (Button) event.getSource();   // Grt the clicked button
        int row = Integer.parseInt(button.getId().substring(2, 3));   // Get the row index
        int col = Integer.parseInt(button.getId().substring(3, 4));   // Get the column index

        if (board.isLegalMove(row, col)) {  // Check if the move is legal
            // Human player makes a move
            human.move(row, col);
            resetBoard();  // Update the UI with the new move

            // Check if the human player has won or if the board is full
            if (board.checkWinner() != null) {
                NotifyWinner(board.checkWinner().getWinningPiece());
                return;
            } else if (board.isBoardFull()) {
                displayMessage("Match Drawn!!!");
                return;
            }

            // Allow the AI to make a move only if the human’s move was valid
            ai.findBestMove();
            resetBoard();

            // Check if the AI has won or if the board is full after the AI’s move
            if (board.checkWinner() != null) {
                NotifyWinner(board.checkWinner().getWinningPiece());
            } else if (board.isBoardFull()) {
                displayMessage("Match Drawn!!!");
            }
        } else {
            displayMessage("Invalid move! Try again.");    // Display a message if the cell is already occupied.
        }

    }

    public void resetBoard() {
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                update(i, j, board.getPieces()[i][j]);
            }
        }
    }

    public void initialize() {   // Initialize button references to Array
        buttons[0][0] = id00;
        buttons[0][1] = id01;
        buttons[0][2] = id02;
        buttons[1][0] = id10;
        buttons[1][1] = id11;
        buttons[1][2] = id12;
        buttons[2][0] = id20;
        buttons[2][1] = id21;
        buttons[2][2] = id22;
    }

    @Override
    public void update(int row, int col, Piece piece) {
        Button button = buttons[row][col];

        if (piece == Piece.X) {
            button.setText("X");
        } else if (piece == Piece.O) {
            button.setText("O");
        } else {
            button.setText("");
        }
    }

    @Override
    public void NotifyWinner(Piece winner) {
        if (winner == Piece.X) {
            displayMessage("X Won!!!");
        } else if (winner == Piece.O) {
            displayMessage("O Won!!!");
        }
    }

    private void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setOnCloseRequest((DialogEvent event) -> {
            board.initializeBoard();
            resetBoard();
        });
        alert.showAndWait();
    }
}

