package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.IncorrectPasswordException;
import org.loose.fis.sre.exceptions.InvalidUsernameException;
import org.loose.fis.sre.services.UserService;
import javafx.scene.control.TextField;

import javax.swing.*;

import java.io.IOException;
//import java.awt.*;

public class LoginController {
    @FXML
    private Label logMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logButton;
    @FXML
    private Button regButton;

    public void handleRegistrationView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage scene= (Stage) logButton.getScene().getWindow();
        scene.setTitle("Welcome");
        scene.setScene(new Scene(root,725,490));
    }

    public void  handleLoginAction() throws IOException {
        try
        {
            UserService.userExists(usernameField.getText(),passwordField.getText());
            logMessage.setText("Successful log in");
            if(UserService.checkIsAdmin(usernameField.getText())==true)
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_main_page.fxml"));
                Stage scene= (Stage) logButton.getScene().getWindow();
                scene.setTitle("Admin");
                scene.setScene(new Scene(root,725,490));
            }
            else
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client_main_page.fxml"));
                Stage scene= (Stage) logButton.getScene().getWindow();
                scene.setScene(new Scene(root,1280,720));
//                scene.setHeight(1080);
//                scene.setWidth(1920);
                scene.setTitle("Client");
                scene.setFullScreen(true);
            }


        }
        catch(InvalidUsernameException e)
        {
            logMessage.setText(e.getMessage());

        }catch(IncorrectPasswordException e)
        {
            logMessage.setText(e.getMessage());
        }

    }


}
