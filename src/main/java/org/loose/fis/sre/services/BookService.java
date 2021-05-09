package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.BookIDExistsException;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.User;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BookService {
    public static ObjectRepository<Book> bookRepository;


    public static void initDatabase() {

        Nitrite database= Nitrite.builder()
                .filePath(getPathToFile("books.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        bookRepository= database.getRepository(Book.class);
    }
    public static void addBook(String id,String titlu,String autor,String limba,String gen,String dom,String path,String description) throws BookIDExistsException
    {
        bookRepository.insert(new Book(id,titlu,autor,limba,gen,dom,path,description));
    }
    public static void checkBookDoesNotAlreadyExists(String id) throws BookIDExistsException
    {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (id.equals(book.getBook_id())) {
                throw new BookIDExistsException("This ID already exists");

            }
        }
    }
    public static void increaseStoc(String id)
    {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (id.equals(book.getBook_id())) {
                book.increaseNrBook();

            }
        }
    }
}
