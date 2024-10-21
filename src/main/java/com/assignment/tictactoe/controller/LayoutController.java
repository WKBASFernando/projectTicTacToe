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

public class LayoutController implements BoardUI {
    BoardImpl board;
    AiPlayer ai;
    HumanPlayer human;

    public LayoutController() {
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
        Button button = (Button) event.getSource();
        int row = Integer.parseInt(button.getId().split("")[2]);
        int col = Integer.parseInt(button.getId().split("")[3]);

        human.move(row, col);
        ai.findBestMove();
        board.printBoard();
        resetBoard();
        if (board.checkWinner() != null) {
            NotifyWinner(board.checkWinner().getWinningPiece());
        } else if (board.isBoardFull()) {
            displayMessage("Match Drawn!!!");
        }
    }

    public void resetBoard() {
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                update(i, j, board.getPieces()[i][j]);
            }
        }
    }

    @Override
    public void update(int row, int col, Piece piece) {
        String buttonId = "id" + row + col;
        for (Node node : gameGrid.getChildren()) {
            if (node instanceof Button button && buttonId.equals(node.getId())) {
                if (piece == Piece.X) {
                    button.setText("X");
                } else if (piece == Piece.O) {
                    button.setText("O");
                } else {
                    button.setText("");
                }
                break;
            }
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

