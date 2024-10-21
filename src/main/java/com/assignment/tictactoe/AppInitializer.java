package com.assignment.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/BoardView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tic Tac Toe");
        Image image = new Image(getClass().getResourceAsStream("/images/39a931202530759.Y3JvcCw3NDksNTg2LDIwMCw0MzQ.png"));
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }

    public static void main(String[] args) {
        launch();
    }
}