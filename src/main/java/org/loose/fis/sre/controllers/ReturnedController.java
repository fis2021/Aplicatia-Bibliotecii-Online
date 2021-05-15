package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.BookDoesNotExistInLibrary;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.services.BookService;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.io.IOException;

public class ReturnedController {

    @FXML
    TextField Title;

    @FXML
    TextField Username;

    @FXML
    Button backB;

    @FXML
    Button Mark;

    @FXML
    Label marked;

    public void handleMark()  {

        for (BorrowedBook book : BorrowedBooksService.borrowedRepository.find()) {
            if ( Title.getText().equals(book.getB().getTitlu()) && Username.getText().equals(book.getU().getUsername()) && book.isReturned() == false) {
                BorrowedBooksService.setasreturned(book);
                BookService.increaseStoc(book.getB().getTitlu(),book.getB().getAutor(),book.getB().getLimba());
                marked.setText("Carte returnata!");
            }
        }


    }


    public void handleback() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_main_page.fxml"));
        Stage scene= (Stage) backB.getScene().getWindow();
        scene.setScene(new Scene(root,500,500));


    }
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }


}
