package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.loose.fis.sre.model.Book;

public class MiniBookController {
    @FXML
    private ImageView bookView;
    @FXML
    private Text titluText;
    @FXML
    private Text autorText;
    public void setData(Book b)
    {
        Image img=new Image(b.getPhoto_path());
        bookView.setImage(img);
        titluText.setText(b.getTitlu());
        autorText.setText(b.getAutor());
    }

}
