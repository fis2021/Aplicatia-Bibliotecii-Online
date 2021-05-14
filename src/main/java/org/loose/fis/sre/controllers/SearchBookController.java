package org.loose.fis.sre.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.BookDoesNotExistInLibrary;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.services.BookService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchBookController  {

    @FXML
    TextField Title;
    @FXML
    TextField Author;
    @FXML
    TextField Genre;
    @FXML
    TextField Language;
    @FXML
    TextField Category;
    @FXML
    Button searchBook;
    @FXML
    Label bookMessage;
    @FXML
    GridPane grid;


    public void handleSearch() throws IOException {

        try {
            BookService.checkBookExistInLibrary(Title.getText(), Author.getText(), Genre.getText(), Language.getText(), Category.getText());

        } catch (BookDoesNotExistInLibrary e) { bookMessage.setText(e.getMessage());
        }


    }


}




