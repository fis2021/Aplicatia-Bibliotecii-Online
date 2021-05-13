package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.BookExistsException;
import org.loose.fis.sre.exceptions.UncompletedFieldsException;
import org.loose.fis.sre.model.Book;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BookService {
    public static ObjectRepository<Book> bookRepository;


    public static void initDatabase() {

            Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("books.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        bookRepository = database.getRepository(Book.class);
    }

    public static void addBook(String titlu, String autor, String limba, String gen, String dom, String path, String description) throws  UncompletedFieldsException,BookExistsException {
        AllFieldsCompleted(titlu, autor, dom, description);
        checkBookDoesNotAlreadyExists(titlu, autor, limba);
        Book b = new Book(titlu, autor, limba, gen, dom, path, description);
        UUID u = b.getBook_id();
        while (checkIDisUnic(u) == false) {
            u = b.rando();
            checkIDisUnic(u);
        }
        bookRepository.insert(b);
    }

    public static void checkBookDoesNotAlreadyExists(String titlu, String autor, String limba) throws BookExistsException {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (titlu.equals(book.getTitlu())) {
                if (autor.equals(book.getAutor())) {
                    if (limba.equals(book.getLimba())) {
                        throw new BookExistsException("Cartea există deja în bibliotecă!");
                    }
                }

            }
        }
    }

    public static boolean checkIDisUnic(UUID u) {
        Cursor<Book> cursor = bookRepository.find();
        for (Book book : cursor) {
            if (u.equals(book.getBook_id())) {
                return false;
            }
        }
        return true;

    }

    public static void increaseStoc(String titlu, String autor, String limba) {
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

    public static boolean AllFieldsCompleted(String titlu, String autor,String categorie,String descr) throws UncompletedFieldsException {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher1 = pattern.matcher(titlu);
        Matcher matcher2 = pattern.matcher(autor);
        Matcher matcher3 = pattern.matcher(categorie);
        Matcher matcher4 = pattern.matcher(descr);
        boolean matchFound1 = matcher1.find();
        boolean matchFound2 = matcher2.find();
        boolean matchFound4 = matcher3.find();
        boolean matchFound5 = matcher4.find();
        if (!matchFound1) throw new UncompletedFieldsException("You must complete all the fields!");
        if (!matchFound2) throw new UncompletedFieldsException("You must complete all the fields!");
        if (!matchFound4) throw new UncompletedFieldsException("You must complete all the fields!");
        if (!matchFound5) throw new UncompletedFieldsException("You must complete all the fields!");


        return true;


    }
   /* public static void deleteRecord() {
        Cursor<Book> cursor = bookRepository.find();
        int cnt=0;
        for (Book book : cursor) {
           if(book.getTitlu().equals("t"))
           {
               bookRepository.remove(book);
           }

        }
    }*/
   public static ArrayList<Book> getLast() {
       Cursor<Book> cursor = bookRepository.find();
       ArrayList<Book> a=new ArrayList<Book>();
       int cnt = cursor.totalCount();
       int cnt2=0;
       for (Book book : cursor) {
           cnt2++;
           if(cnt2==cnt-2)
           {
               a.add(book);
           }
           if(cnt2==cnt-1)
           {
               a.add(book);
           }
           if(cnt2==cnt)
           {
               a.add(book);
           }
       }
       return a;

   }

}
