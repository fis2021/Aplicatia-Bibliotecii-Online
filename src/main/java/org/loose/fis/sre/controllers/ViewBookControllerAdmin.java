package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.model.SelectedClient;
import org.loose.fis.sre.services.BookService;
import org.loose.fis.sre.services.BorrowedBooksService;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ViewBookControllerAdmin implements Initializable {
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
    private Button backButton;
    @FXML
    private Button bcButton;
    @FXML
    private Button returnButton;

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

    }
    public void handleback() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_main_page.fxml"));
        Stage scene= (Stage) backButton.getScene().getWindow();
        scene.setScene(new Scene(root,1920,1080));
        scene.setResizable(false);
        scene.setMinHeight(1080);
        scene.setMinWidth(1920);
        scene.setMaxHeight(1080);
        scene.setMaxWidth(1920);
        scene.setTitle("Admin");
        scene.setFullScreen(true);

    }
    public void handleBack() throws IOException {


            Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("client_books.fxml"));
            Stage scene = (Stage) bcButton.getScene().getWindow();
            scene.setScene(new Scene(root,1920,1080));
            scene.setResizable(false);
            scene.setMinHeight(1080);
            scene.setMinWidth(1920);
            scene.setMaxHeight(1080);
            scene.setMaxWidth(1920);
            scene.setTitle("Client and his books");
            scene.setFullScreen(true);

    }
    public void handleMark()  {
        returnButton.setOnMouseClicked(v-> {
            BorrowedBooksService.setasreturnedd(ClickedBook.selectedBook,SelectedClient.selectedClient);

        });}



    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
