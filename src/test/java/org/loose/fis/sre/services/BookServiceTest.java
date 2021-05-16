package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.BookDoesNotExistInLibrary;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.model.Book;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;



class BookServiceTest {
    private static final String Titlu="Aminitiri din copilarie";
    private static final String Autor="Ion Creanga";
    private static final String Limba="Romana";
    private static final String Gen="Epic";
    private static final String Categorie="Memorialistic";
    private static final String Path="@images/book_images/Book1";
    private static final String Descriere="Cartea tuturor copiilor";
    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER=".test-books";
        FileSystemService.initDirectory();

    }

    @AfterAll
    static void afterAll() throws IOException {

        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());

    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-books";
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());
        BookService.initDatabase();


    }

    @AfterEach
    void tearDown() {
        BookService.closeDatabase();
    }

    @Test
    @DisplayName("Database is initialized and there are no books")
    void DataBaseIsInitializedAndNoUserIsInIt()
    {

        assertThat(BookService.getAllBooks()).isNotNull();
        assertThat(BookService.getAllBooks()).isEmpty();



    }
    @Test
    @DisplayName("Books is added to database")
    void TestBookIsAddedToDatabase() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        assertThat(BookService.getAllBooks()).isNotEmpty();
        assertThat(BookService.getAllBooks()).size().isEqualTo(1);
        assertThat(BookService.getAllBooks().get(0).getAutor()).isEqualTo(Autor);
        assertThat(BookService.getAllBooks().get(0).getTitlu()).isEqualTo(Titlu);
        assertThat(BookService.getAllBooks().get(0).getLimba()).isEqualTo(Limba);
        assertThat(BookService.getAllBooks().get(0).getDescription()).isEqualTo(Descriere);
        assertThat(BookService.getAllBooks().get(0).getDom_stiintific()).isEqualTo(Categorie);
        assertThat(BookService.getAllBooks().get(0).getPhoto_path()).isEqualTo(Path);
        assertThat(BookService.getAllBooks().get(0).getStoc()).isEqualTo(1);
    }
    @Test
    @DisplayName("Check if book is already in database")
    void TestBookIsAlreadyInDatabase()
    {
        assertThrows(BookExistsException.class,()->{
            BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
            BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        });
    }

    @Test
    @DisplayName("Book is not in database")
    void TestBookNotExistsInDatabase ()
    {
        assertThrows(BookDoesNotExistInLibrary.class,()->
        {
            BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
            BookService.checkBookExistInLibrary(Titlu+1,Autor,Gen,Limba,Categorie);
        });
    }
    @Test
    @DisplayName("Uncompleted fields check")
    void TestUncompletedFields()
    {
        assertThrows(UncompletedFieldsException.class,()->
        {
            BookService.addBook(Titlu,"",Limba,Gen,Categorie,Path,Descriere);

        });
        assertThrows(UncompletedFieldsException.class,()->
        {

            BookService.addBook("",Autor,Limba,Gen,Categorie,Path,Descriere);


        });

        assertThrows(UncompletedFieldsException.class,()->
        {

            BookService.addBook(Titlu,Autor,Limba,Gen,"",Path,Descriere);

        });

        assertThrows(UncompletedFieldsException.class,()->
        {

            BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,"");

        });
    }
    @Test
    @DisplayName("Id is unic")
    void TestIdIsUnic() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu,Autor+1,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu+1,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu,Autor,Limba+1,Gen,Categorie,Path,Descriere);
        assertThat(BookService.checkIDisUnic(((new Book(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere)).getBook_id()))).isEqualTo(true);

    }
    @Test
    @DisplayName("Stock of book was increased")
    void TestIncreaseBook() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.increaseStoc(Titlu,Autor,Limba);
        assertThat(BookService.getAllBooks().get(0).getStoc()).isEqualTo(2);
    }
    @Test
    @DisplayName("Stock of book was increased")
    void TestDecreaseBook() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.decrementStoc(Titlu,Autor,Limba);
        assertThat(BookService.getAllBooks().get(0).getStoc()).isEqualTo(0);
    }
    @Test
    @DisplayName("The book was deleted")
    void testDeleting() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu+1,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.deleteRecord(Titlu);
        assertThat(BookService.getAllBooks().size()).isEqualTo(1);
        assertThat(BookService.getAllBooks().get(0).getTitlu()).isEqualTo(Titlu+1);
    }
    @Test
    @DisplayName("The last 3 books added are stored correctly")
    void TestNewestBooks() throws UncompletedFieldsException, BookExistsException {
        BookService.addBook(Titlu,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu+1,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu+2,Autor,Limba,Gen,Categorie,Path,Descriere);
        BookService.addBook(Titlu+3,Autor,Limba,Gen,Categorie,Path,Descriere);
        assertThat(BookService.getLast().get(0).getTitlu()).isEqualTo(Titlu+1);
        assertThat(BookService.getLast().get(1).getTitlu()).isEqualTo(Titlu+2);
        assertThat(BookService.getLast().get(2).getTitlu()).isEqualTo(Titlu+3);
        assertThat(BookService.getLast().size()).isEqualTo(3);
    }

}