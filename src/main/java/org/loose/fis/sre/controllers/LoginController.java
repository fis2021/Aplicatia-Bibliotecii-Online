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
//import java.awt.*;

public class LoginController {
    @FXML
    private Label logMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    public void  handleLoginAction()
    {
        try
        {
            UserService.userExists(usernameField.getText(),passwordField.getText());
            logMessage.setText("Successful log in");

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
