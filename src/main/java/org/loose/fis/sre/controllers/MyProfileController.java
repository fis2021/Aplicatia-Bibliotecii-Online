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
     Button borrowedBooks;

    @FXML
    Button backButton;

    public void handleback() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client_main_page.fxml"));
        Stage scene= (Stage) backButton.getScene().getWindow();
        scene.setScene(new Scene(root,500,500));



    }

    public void handleSeeListOfBorrowedBooks() throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("MyBorrowedBooksPage.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        MyBorrowedBooksController controller = loader.getController();
        controller.setBorrowedBooksList();
        Stage stage = (Stage) (borrowedBooks.getScene().getWindow());
        stage.setTitle("Borrowed Book List");
        stage.setScene(scene);
        stage.show();
    }




}
