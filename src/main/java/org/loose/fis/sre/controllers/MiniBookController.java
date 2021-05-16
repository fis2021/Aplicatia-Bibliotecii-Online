package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.MyListener;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.ClickedBook;

import java.io.IOException;

public class MiniBookController {
    @FXML
    private HBox anch;
    @FXML
    private ImageView bookView;
    @FXML
    private Text titluText;
    @FXML
    private Text autorText;
    @FXML
    private void clicked(MouseEvent mouseEvent) throws IOException {
        myListener.onCLickListener(book);
        ClickedBook.selectedBook=book;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("bookAdmin.fxml"));
        Stage scene= (Stage)bookView.getScene().getWindow();
        scene.setScene(new Scene(root,1920,1080));
        scene.setResizable(false);
        scene.setMinHeight(1080);
        scene.setMinWidth(1920);
        scene.setMaxHeight(1080);
        scene.setMaxWidth(1920);
        scene.setTitle("Book");
        scene.setFullScreen(true);

    }
    private Book book;
    private MyListener myListener;
    public void setData(Book b,MyListener myListener)
    {   this.book=b;
        this.myListener=myListener;
        Image img=new Image(b.getPhoto_path());
        bookView.setImage(img);
        titluText.setText(b.getTitlu());
        autorText.setText(b.getAutor());
    }
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }



}
