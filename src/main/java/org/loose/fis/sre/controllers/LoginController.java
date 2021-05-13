package org.loose.fis.sre.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.h2.mvstore.cache.CacheLongKeyLIRS;
import org.loose.fis.sre.exceptions.IncorrectPasswordException;
import org.loose.fis.sre.exceptions.InvalidUsernameException;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.UserService;
import javafx.scene.control.TextField;

import javax.swing.*;

import java.io.IOException;
import java.io.ObjectInputFilter;
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
            LoggedUser.loggedUser=UserService.userExists(usernameField.getText(),passwordField.getText());
            logMessage.setText("Successful log in");
            if(UserService.checkIsAdmin(usernameField.getText())==true)
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_main_page.fxml"));
                Stage scene= (Stage) logButton.getScene().getWindow();
                scene.setTitle("Admin");
                scene.setScene(new Scene(root,1920,1080));
                scene.setResizable(false);
                scene.setMinHeight(1080);
                scene.setMinWidth(1920);
                scene.setMaxHeight(1080);
                scene.setMaxWidth(1920);
                scene.setTitle("Admin");
                scene.setFullScreen(true);


            }
            else
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client_main_page.fxml"));
                Stage scene= (Stage) logButton.getScene().getWindow();
                scene.setScene(new Scene(root,1920,1080));
                scene.setResizable(false);
                scene.setMinHeight(1080);
                scene.setMinWidth(1920);
                scene.setMaxHeight(1080);
                scene.setMaxWidth(1920);
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
