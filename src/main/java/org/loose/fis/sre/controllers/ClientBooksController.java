package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.loose.fis.sre.MyListener;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.services.BorrowedBooksService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientBooksController implements Initializable  {

    @FXML
    private HBox noreturned;
    @FXML
    private HBox rtrn;
    @FXML
    private Button bcButton;
    private MyListener myListener;

       @Override
       public void initialize(URL url, ResourceBundle resourceBundle) {
         ArrayList<Book> a=BorrowedBooksService.borrowedOnesandNotReturnedForSelectedClient();
         ArrayList<Book> b=BorrowedBooksService.borrowedOnesandReturnedForSelectedClient();
         myListener=new MyListener()
         {
             @Override
             public void onCLickListener(Book b) {
                ClickedBook.selectedBook=b;

             }



         };
         try
         {
             for(int i=0;i<a.size();i++)
             {
                 FXMLLoader fxmlLoader=new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getClassLoader().getResource("book_mini.fxml"));
                 HBox anch=fxmlLoader.load();
                 MiniBookController miniBookController=fxmlLoader.getController();
                 miniBookController.setData(a.get(i),myListener);
                 noreturned.getChildren().add(anch);
             }
         } catch (Exception e) {
             e.printStackTrace();


         }
           try
           {
               for(int i=0;i<b.size();i++)
               {
                   FXMLLoader fxmlLoader=new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getClassLoader().getResource("book_mini.fxml"));
                   HBox anch=fxmlLoader.load();
                   MiniBookController miniBookController=fxmlLoader.getController();
                   miniBookController.setData(a.get(i),myListener);
                   rtrn.getChildren().add(anch);
               }
           } catch (Exception e) {
               e.printStackTrace();


           }


    }
    public void handleback() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("allclients.fxml"));
        Stage scene= (Stage) bcButton.getScene().getWindow();
        scene.setScene(new Scene(root,1920,1080));
        scene.setResizable(false);
        scene.setMinHeight(1080);
        scene.setMinWidth(1920);
        scene.setMaxHeight(1080);
        scene.setMaxWidth(1920);
        scene.setTitle("Admin");
        scene.setFullScreen(true);
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
