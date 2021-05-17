package org.loose.fis.sre;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.exceptions.NoUpperCaseException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.BookService;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class LoginTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());
        UserService.initDatabase();
        BookService.initDatabase();


    }
    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
        BookService.closeDatabase();
    }
    @Start
    void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 725, 490));
        primaryStage.show();
    }
    @Test
    void testLogin_IncorrectUsername(FxRobot robot) throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addUser("maria","Iasmina","Purcar Iasmina","iasmina2yahoo.com","Deva","07789646");
        robot.clickOn("#usernameLog");
        robot.write("iasmina");
        robot.clickOn("#passwordLog");
        robot.write("Iasminaa");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryLabeled()).hasText("Introduced username is incorrect");



    }
    @Test
    void testLogin_IncorrectPassword(FxRobot robot) throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addUser("Iasmina","Iasminaa","Purcar Iasmina","iasmina2yahoo.com","Deva","07789646");
        robot.clickOn("#usernameLog");
        robot.write("Iasmina");
        robot.clickOn("#passwordLog");
        robot.write("Iasmina");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryLabeled()).hasText("Introduced password is incorrect");
    }
    @Test
    void testLogin_Successful_Client(FxRobot robot) throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException, IOException {
        UserService.addUser("Iasmina","Iasmina","Purcar Iasmina","iasmina2yahoo.com","Deva","07789646");
        robot.clickOn("#usernameLog");
        robot.write("Iasmina");
        robot.clickOn("#passwordLog");
        robot.write("Iasmina");
        robot.clickOn("#loginButton");
        FxAssert.verifyThat(robot.window("Client"), WindowMatchers.isShowing());
    }
    @Test
    void testLogin_Successful_Admin(FxRobot robot) throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException, IOException {
        UserService.addAdmin("Iasmina","Iasmina","Purcar Iasmina","iasmina2yahoo.com","Deva","07789646");
        robot.clickOn("#usernameLog");
        robot.write("Iasmina");
        robot.clickOn("#passwordLog");
        robot.write("Iasmina");
        robot.clickOn("#loginButton");
        FxAssert.verifyThat(robot.window("Admin"), WindowMatchers.isShowing());
    }


}