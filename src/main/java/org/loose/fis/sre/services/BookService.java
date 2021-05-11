package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.model.Book;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BookService {
    public static ObjectRepository<Book> bookRepository;


    public static void initDatabase() {

        Nitrite database= Nitrite.builder()
                .filePath(getPathToFile("books.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        bookRepository= database.getRepository(Book.class);
    }
    public static void addBook(String titlu,String autor,String limba,String gen,String dom,String path,String description) throws BookExistsException
    {   checkBookDoesNotAlreadyExists(titlu,autor,limba);
        bookRepository.insert(new Book(titlu,autor,limba,gen,dom,path,description));
    }
    public static void checkBookDoesNotAlreadyExists(String titlu, String autor, String limba) throws BookExistsException
    {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (titlu.equals(book.getTitlu())) {
                if(autor.equals(book.getAutor()))
                {
                    if(limba.equals(book.getLimba())) {
                        throw new BookExistsException("Cartea există deja în bibliotecă!");
                    }
                }

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
