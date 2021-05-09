package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

public class AddBookController implements Initializable{
    @FXML

    private ComboBox LanguageBox;
    @FXML
    private AnchorPane rootElement;
    @FXML
    private Button selectFileButton;
    @FXML
    private ImageView bookImage;

    public void selectFileAction() {
        Stage stage = (Stage) selectFileButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        Image fxImage = new Image(new File(selectedFile.getAbsolutePath()).toURI().toString());
        bookImage.setImage(fxImage);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LanguageBox.getItems().addAll("Română", "Engleză", "Franceză", "Germană");


    }
}
