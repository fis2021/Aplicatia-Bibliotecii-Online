package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.Already3BooksBorrowedException;
import org.loose.fis.sre.exceptions.AlreadyBorrowedException;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewBookController implements Initializable {
     @FXML
     private ImageView imgView;
     @FXML
     private Text titluText;
     @FXML
     private TextArea descArea;
    @FXML
    private Text lgText;
    @FXML
    private Text catText;
    @FXML
    private Text genText;
    @FXML
    private Button borrowButton;
    @FXML
    private Text borrowMessage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img=new Image(ClickedBook.selectedBook.getPhoto_path());
        imgView.setImage(img);
        titluText.setText(ClickedBook.selectedBook.getTitlu());
        descArea.setText(ClickedBook.selectedBook.getDescription());
        descArea.setEditable(false);
        lgText.setText(ClickedBook.selectedBook.getLimba());
        catText.setText(ClickedBook.selectedBook.getDom_stiintific());
        genText.setText(ClickedBook.selectedBook.getGen_literar());

    }

    public void handleBorrow()
    {   borrowButton.setOnAction(v-> {
        try {
            BorrowedBooksService.addBorrowedBook(LoggedUser.loggedUser, ClickedBook.selectedBook);
            borrowMessage.setText("Carte împrumutată cu succes");
            ClickedBook.selectedBook.decrementNrBook();
        } catch (Already3BooksBorrowedException e) {
            borrowMessage.setText(e.getMessage());
        }
        catch(AlreadyBorrowedException e)
        {
            borrowMessage.setText(e.getMessage());
        }
         });
    }
}
