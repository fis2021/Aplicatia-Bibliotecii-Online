package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.loose.fis.sre.services.BorrowedBooksService.ALready3Books;

public class ViewBookControllerClient implements Initializable {
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
    @FXML
    Button gobackbutton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = new Image(ClickedBook.selectedBook.getPhoto_path());
        imgView.setImage(img);
        titluText.setText(ClickedBook.selectedBook.getTitlu());
        descArea.setText(ClickedBook.selectedBook.getDescription());
        descArea.setEditable(false);
        lgText.setText(ClickedBook.selectedBook.getLimba());
        catText.setText(ClickedBook.selectedBook.getDom_stiintific());
        genText.setText(ClickedBook.selectedBook.getGen_literar());
        if (BorrowedBooksService.checkIfThisBookIsBorrowedByThaSameUser(LoggedUser.loggedUser, ClickedBook.selectedBook) == false) {
            borrowButton.setDisable(true);
            borrowMessage.setText("Ai deja această carte împrumutată!");
        } else if (BorrowedBooksService.CheckIfThisBookStockIsOk(ClickedBook.selectedBook) == false) {
            borrowButton.setDisable(true);
            borrowMessage.setText("Această carte nu este disponibilă!");
        } else if (ALready3Books(LoggedUser.loggedUser) == false) {

            borrowButton.setDisable(true);
            borrowMessage.setText("Ai depasit numarul maxim de imprumuturi!");

        }
    }
    public void handleback() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("SearchABook.fxml"));
        Stage scene= (Stage) gobackbutton.getScene().getWindow();
        scene.setScene(new Scene(root,500,500));



    }

    public void handleBorrow()
    {

            BorrowedBooksService.addBorrowedBook(LoggedUser.loggedUser, ClickedBook.selectedBook);
            borrowButton.setDisable(true);
            borrowMessage.setText("Carte împrumutată cu succes");
            ClickedBook.selectedBook.decrementNrBook();

    }


}

