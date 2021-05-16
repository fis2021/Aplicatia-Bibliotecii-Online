package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class BorrowedBooksServiceTest {

    private static final User u=new User("alina","parola","alinuta","rozelor","alina@yahoo.com","0723443");
    private static final Book b=new Book("Aminitiri din copilarie","Ion Creanga","Română", "Epic","Memorialistic","@images/book_images/Book1","Cartea tuturor copiilor");


    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER=".test-borrow";
        FileSystemService.initDirectory();

    }

    @AfterAll
    static void afterAll() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());
    }
    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-borrow";
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());
        BorrowedBooksService.initDatabase();
    }
    @AfterEach
    void tearDown() {
        BorrowedBooksService.closeDatabase();
    }


    @Test
    void VerifyIfDataBaseIsInitialized() {

        assertThat(BorrowedBooksService.getAllBooks()).isNotNull();
        assertThat(BorrowedBooksService.getAllBooks()).isEmpty();

    }
    @Test
    @DisplayName("Books is added to database")
    void TestBookIsAddedToDatabase() throws BookExistsException {
        BorrowedBooksService.addBorrowedBook(u,b);
        assertThat(BorrowedBooksService.getAllBooks()).isNotEmpty();
        assertThat(BorrowedBooksService.getAllBooks()).size().isEqualTo(1);
        assertThat(BorrowedBooksService.getAllBooks().get(0).getU()).isEqualTo(u);
        assertThat(BorrowedBooksService.getAllBooks().get(0)).isEqualTo(b);

    }
    @Test
    @DisplayName("Books is returned")
    void TestBookIsReturned() {
        BorrowedBooksService.setasreturned(new BorrowedBook(b,u));
        assertThat(BorrowedBooksService.getAllBooks().get(0).isReturned()).isEqualTo(true);


    }
    @Test
    @DisplayName("Borrowed by the same user")
    void Borrowedbyuser() {
        BorrowedBooksService.checkIfThisBookIsBorrowedByThaSameUser(u,b);
        assertThat(BorrowedBooksService.getAllBooks().get(0).isReturned()).isEqualTo(false);


    }
    @Test
    @DisplayName("If the book stoc is ok")
    void Bookstoc() {
        BorrowedBooksService.CheckIfThisBookStockIsOk(b);
        assertThat(BorrowedBooksService.getAllBooks().get(0).getB().getStoc()).isEqualTo(true);


    }



}