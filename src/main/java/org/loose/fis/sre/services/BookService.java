package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.model.Book;

import java.util.UUID;

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
        Book b=new Book(titlu,autor,limba,gen,dom,path,description);
        UUID u=b.getBook_id();
        while(checkIDisUnic(u)==false)
        {
            u=b.rando();
            checkIDisUnic(u);
        }
        bookRepository.insert(b);
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
    public static  boolean  checkIDisUnic(UUID u)
    {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (u.equals(book.getBook_id())) {
            return false;
            }
        }
        return true;

    }

   public static void increaseStoc(String titlu,String autor,String limba) {
       Cursor<Book> cursor = bookRepository.find();
       for (Book book : cursor) {
           if (titlu.equals(book.getTitlu())) {
               if (autor.equals(book.getAutor())) {

                   if (limba.equals(book.getLimba())) {
                       book.increaseNrBook();
                       bookRepository.update(book);

                   }

               }
           }
       }
   }
}
