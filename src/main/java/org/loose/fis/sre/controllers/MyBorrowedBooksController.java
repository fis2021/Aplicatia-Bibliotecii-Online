package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.util.Objects;

public class MyBorrowedBooksController {

    @FXML
    ListView<String>  borrowedBooksList = new ListView<String>();


    public void setBorrowedBooksList() {

        String aux;
        ObservableList<String> items = FXCollections.observableArrayList();
        for (BorrowedBook book : BorrowedBooksService.borrowedRepository.find()) {
            if (LoggedUser.loggedUser.equals(book.getU()) ) {
                aux = book.getB().getTitlu() ;
                items.add(aux);
                borrowedBooksList.setItems(items);
            }
        }
    }


}
