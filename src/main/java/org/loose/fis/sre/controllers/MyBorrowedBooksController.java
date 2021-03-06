package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.io.IOException;
import java.util.Objects;

public class MyBorrowedBooksController {

    @FXML
    ListView<String>  borrowedBooksList = new ListView<String>();
    @FXML
    Button backB;

    public void handlebackB() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MyProfilePage.fxml"));
        Stage scene= (Stage) backB.getScene().getWindow();
        scene.setScene(new Scene(root,500,500));
        scene.setFullScreen(true);
        scene.setResizable(false);
        scene.setMinHeight(700);
        scene.setMinWidth(700);



    }


    public void setBorrowedBooksList() {

        String aux;
        ObservableList<String> items = FXCollections.observableArrayList();
        for (BorrowedBook book : BorrowedBooksService.borrowedRepository.find()) {
            if (LoggedUser.loggedUser.equals(book.getU()) && !book.isReturned() ) {
                aux = book.getB().getTitlu() +" de "+ book.getB().getAutor() ;
                items.add(aux);
                borrowedBooksList.setItems(items);
            }
        }
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }
}
