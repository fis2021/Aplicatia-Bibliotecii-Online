package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.NoUpperCaseException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;

import static org.loose.fis.sre.services.UserService.UpperCaseExists;

public class RegistrationController {

    @FXML
    private Label registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button logButton;

    public void handleRegisterAction() {

     try{
       UserService.addUser(usernameField.getText(), passwordField.getText(),nameField.getText(),emailField.getText(),addressField.getText(),phoneField.getText());
       registrationMessage.setText("Account created successfully!");
       handleLogAction();
        }catch(UncompletedFieldsException e){
         registrationMessage.setText(e.getMessage());
        }catch(UsernameAlreadyExistsException ee)
       {
         registrationMessage.setText(ee.getMessage());
       }catch(NoUpperCaseException eee)
     {
         registrationMessage.setText(eee.getMessage());
     } catch (Exception e) {
         e.printStackTrace();
     }


    }
   public void handleLogAction() throws Exception {
       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
       Stage scene= (Stage) logButton.getScene().getWindow();
        scene.setScene(new Scene(root,725,490));

    }
}
