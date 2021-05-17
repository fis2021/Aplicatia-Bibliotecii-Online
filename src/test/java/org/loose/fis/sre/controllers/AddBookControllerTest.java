package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.services.BookService;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.UserService;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.ComboBoxMatchers;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class AddBookControllerTest {
    private static final String Titlu="Aminitiri din copilarie";
    private static final String Autor="Ion Creanga";
    private static final String Limba="Romana";
    private static final String Gen="Epic";
    private static final String Categorie="Memorialistic";
    private static final String Path="file://E:\\Proiect_FIS\\BAZADEDATE\\project\\src\\main\\resources\\book_images\\book1.jpg";
    private static final String Descriere="Cartea tuturor copiilor";
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        try {
            UserService.closeDatabase();
            BookService.closeDatabase();
        } catch (Exception e){

        }
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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("add_book.fxml"));
        primaryStage.setTitle("Add book");
        primaryStage.setScene(new Scene(root, 730, 700));
        primaryStage.show();
    }
    @Test
    void AddTest(FxRobot robot) throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        robot.clickOn("#titluAdd");
        robot.write(Titlu);
        robot.clickOn("#AutorAdd");
        robot.write(Autor);
        assertThat( ComboBoxMatchers.hasSelectedItem("Romana"));
        robot.clickOn("#languageBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        assertThat( ComboBoxMatchers.hasSelectedItem("Romana"));
        robot.clickOn("#Categorie");
        robot.write(Categorie);
        assertThat( ComboBoxMatchers.hasSelectedItem("Epic"));
        robot.clickOn("#Genbox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        assertThat( ComboBoxMatchers.hasSelectedItem("Epic"));
        robot.clickOn("#Descriere");
        robot.write(Descriere);
        robot.clickOn("#AddBook");
        assertThat(robot.lookup("#Addmessage").queryText()).hasText("You must complete all the fields!");

    }
    @Test
    void testBack(FxRobot robot)
    {
        robot.clickOn("#GoToMain");
        FxAssert.verifyThat(robot.window("Admin"), WindowMatchers.isShowing());
    }
}