package org.loose.fis.sre.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.model.SelectedClient;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllClientsController implements Initializable {
    @FXML
    private TextField sField;
    @FXML
    private TableView <Client> tableView;
    @FXML
    private TableColumn<Client,String> usernameCl;
    @FXML
    private TableColumn<Client,String> nameCl;
    @FXML
    private TableColumn<Client,String> emailCl;
    @FXML
    private TableColumn<Client,String> phoneCl;
    @FXML
    private TableColumn<Client,String> addressCl;
    @FXML
    private Button backButton;
    @FXML
    private Cell<Client> selected;

    private final ObservableList<Client> usersList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameCl.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameCl.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCl.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCl.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCl.setCellValueFactory(new PropertyValueFactory<>("address"));
        ArrayList<User> ar=UserService.AllUsers();
        for(User u:ar)
        {
            usersList.add(new Client(u.getUsername(),u.getName(),u.getEmail(),u.getPhone(),u.getAddress()));
        }
        tableView.setItems(usersList);

        FilteredList<Client> filteredList=new FilteredList<>(usersList,b->true);
        sField.textProperty().addListener((observable,oldValue,newValue)->
        {
            filteredList.setPredicate(client->{
            if(newValue==null || newValue.isEmpty())

                return true;
            if(client.getName().toLowerCase().indexOf(newValue.toLowerCase())!=-1)
                return true;
            else
                return false;
            });
        });
        SortedList<Client> srtcl=new SortedList<>(filteredList);
        srtcl.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(srtcl);

        tableView.setRowFactory( tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Client rowData = row.getItem();
                    SelectedClient.selectedClient=rowData;
                    Parent root=null;
                    try {
                         root = FXMLLoader.load(getClass().getClassLoader().getResource("client_books.fxml"));

                    } catch (IOException e) {
//                        e.printStackTrace();
                    }
                    Stage scene= (Stage) row.getScene().getWindow();
                    scene.setScene(new Scene(root,1920,1080));
                    scene.setResizable(false);
                    scene.setMinHeight(1080);
                    scene.setMinWidth(1920);
                    scene.setMaxHeight(1080);
                    scene.setMaxWidth(1920);
                    scene.setTitle("Client and his books");
                    scene.setFullScreen(true);


                }
            });
            return row ;
        });
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
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
