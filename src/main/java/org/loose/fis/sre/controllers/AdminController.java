package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML
    private Button addBookButton;
    public void handleAddBook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("add_book.fxml"));
        Stage scene= (Stage) addBookButton.getScene().getWindow();
        scene.setTitle("Add Book");
        scene.setScene(new Scene(root,730,700));
        scene.setFullScreen(false);
        scene.setResizable(false);
        scene.setMinHeight(700);
        scene.setMinWidth(730);
        scene.setMaxHeight(700);
        scene.setMaxWidth(730);
    }

}
