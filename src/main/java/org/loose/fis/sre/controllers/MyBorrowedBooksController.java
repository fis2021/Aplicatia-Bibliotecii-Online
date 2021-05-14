package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.util.Objects;

public class MyBorrowedBooksController {

    @FXML
    public ListView borrowedBooksList;


    public void setBorrowedBooksList() {

        String aux;

        for (BorrowedBook book : BorrowedBooksService.borrowedRepository.find()) {
            if (LoggedUser.loggedUser.equals(book.getU()) ) {
                aux = book.getB().getTitlu() ;
                borrowedBooksList.getItems().add(aux);
            }
        }
    }





}
