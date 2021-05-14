package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MyProfileController {

    @FXML
     Button borrowedBooksList;

    public void handleSeeListOfBorrowedBooks() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("MyBorrowedBooksPage.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        MyBorrowedBooksController controller = loader.getController();
        controller.setBorrowedBooksList();
        Stage stage = (Stage) (borrowedBooksList.getScene().getWindow());
        stage.setTitle("Borrowed Book List");
        stage.setScene(scene);
        stage.show();
    }




}
