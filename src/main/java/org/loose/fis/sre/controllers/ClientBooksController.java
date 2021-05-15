package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientBooksController  {

  /*  @FXML
    private HBox noreturned;

       @Override
       public void initialize(URL url, ResourceBundle resourceBundle) {
         ArrayList<Book> a=BorrowedBooksService.borrowedOnesForSelectedClient();
         try
         {
             for(int i=0;i<a.size();i++)
             {
                 FXMLLoader fxmlLoader=new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("book_mini.fxml"));
                 HBox anch=fxmlLoader.load();
                 MiniBookController miniBookController=fxmlLoader.getController();
                 miniBookController.setData(a.get(i));
                 noreturned.getChildren().add(anch);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }


    }*/
}
