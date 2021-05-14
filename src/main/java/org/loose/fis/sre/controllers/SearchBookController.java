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
    Button backButton;

   public void handleback() throws IOException
   {
       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client_main_page.fxml"));
       Stage scene= (Stage) backButton.getScene().getWindow();
       scene.setScene(new Scene(root,500,500));



   }


    public void handleSearch() throws IOException {

        try {
            BookService.checkBookExistInLibrary(Title.getText(),Author.getText(), Genre.getText(), Language.getText(), Category.getText());
            for (Book book : BookService.bookRepository.find()) {
                if (Title.getText().equals(book.getTitlu()) && Author.getText().equals(book.getAutor()) && Genre.getText().equals(book.getGen_literar()) && Language.getText().equals(book.getLimba()) && Category.getText().equals(book.getDom_stiintific()) ) {
                 ClickedBook.selectedBook=book;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("book.fxml"));
            Stage scene= (Stage) searchBook.getScene().getWindow();
            scene.setScene(new Scene(root,1920,1080));
            scene.setResizable(false);
            scene.setMinHeight(1080);
            scene.setMinWidth(1920);
            scene.setMaxHeight(1080);
            scene.setMaxWidth(1920);
            scene.setTitle("Book");
            scene.setFullScreen(true);
        }}}

                catch (BookDoesNotExistInLibrary e) { bookMessage.setText(e.getMessage());
        }


    }


}




