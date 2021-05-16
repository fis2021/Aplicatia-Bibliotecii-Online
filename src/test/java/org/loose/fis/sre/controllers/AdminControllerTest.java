package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.exceptions.NoUpperCaseException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.ClickedBook;
import org.loose.fis.sre.services.BookService;
import org.loose.fis.sre.services.BorrowedBooksService;
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
class AdminControllerTest {
    private static final String Titlu="Aminitiri din copilarie";
    private static final String Autor="Ion Creanga";
    private static final String Limba="Romana";
    private static final String Gen="Epic";
    private static final String Categorie="Memorialistic";
    private static final String Path="src/main/resources/book_images/book1.jpg";
    private static final String Descriere="Cartea tuturor copiilor";

    @BeforeAll
    static void beforeAll() throws IOException {

        FileSystemService.APPLICATION_FOLDER=".test-registration";


        FileSystemService.initDirectory();
        try {
            UserService.closeDatabase();
            BookService.closeDatabase();
        } catch (Exception e){

        }
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());

        UserService.initDatabase();
        BookService.initDatabase();
    }
    @BeforeEach
    void setUp()  {
        FileSystemService.APPLICATION_FOLDER=".test-registration";

    }
    @AfterEach
    void tearDown() {
//        UserService.closeDatabase();
//        BookService.closeDatabase();
    }
    @Start
    void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_main_page.fxml"));
        primaryStage.setTitle("Admin");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
   @Test
    void testAdminReturnABook(FxRobot robot) throws UncompletedFieldsException, BookExistsException {
        robot.clickOn("#returnButtonn");
        FxAssert.verifyThat(robot.window("Return book"), WindowMatchers.isShowing());
    }

    @Test
    void testBorrow(FxRobot robot) throws UncompletedFieldsException, BookExistsException {
        robot.clickOn("#borrowButtonAdmin");
        FxAssert.verifyThat(robot.window("All Clients"), WindowMatchers.isShowing());
    }

    @Test
    void testAdd(FxRobot robot) throws UncompletedFieldsException, BookExistsException {
        robot.clickOn("#addBook");
        FxAssert.verifyThat(robot.window("Add Book"), WindowMatchers.isShowing());
    }
    @Test
    void testLatest(FxRobot robot) throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,"file://E:\\Proiect_FIS\\BAZADEDATE\\project\\src\\main\\resources\\book_images\\book1.jpg",Descriere);
        BookService.addBook(Titlu+" ",Autor,Limba,Gen,Categorie,"file://E:\\Proiect_FIS\\BAZADEDATE\\project\\src\\main\\resources\\book_images\\book1.jpg",Descriere);
        BookService.addBook(Titlu+" i",Autor,Limba,Gen,Categorie,"file://E:\\Proiect_FIS\\BAZADEDATE\\project\\src\\main\\resources\\book_images\\book1.jpg",Descriere);
        ClickedBook.selectedBook=new Book(Titlu,Autor,Limba,Gen,Categorie,"file://E:\\Proiect_FIS\\BAZADEDATE\\project\\src\\main\\resources\\book_images\\book1.jpg",Descriere);
        AdminController.currentBook = ClickedBook.selectedBook;
        robot.clickOn("#imgThread");
        FxAssert.verifyThat(robot.window("Book"), WindowMatchers.isShowing());
    }
    @Test
    void testBack(FxRobot robot)
    {
        robot.clickOn("#OutButtonAdmin");
        FxAssert.verifyThat(robot.window("Welcome"), WindowMatchers.isShowing());
    }
}