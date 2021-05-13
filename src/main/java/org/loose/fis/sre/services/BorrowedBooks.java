package org.loose.fis.sre.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.Book;
import org.loose.fis.sre.model.BorrowedBook;
import org.loose.fis.sre.model.User;

import java.util.UUID;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BorrowedBooks {
    public static ObjectRepository<BorrowedBook> borrowedRepository;


    public static void initDatabase() {

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("borbooks.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        borrowedRepository = database.getRepository(BorrowedBook.class);
    }
    public static void addBorrowedBook(User u, Book b)
    {   BorrowedBook bb=new BorrowedBook(b, u);
        UUID uid=bb.getId();
        while (checkIDisUnic(uid) == false) {
            uid = bb.rando();
            checkIDisUnic(uid);
        }
        borrowedRepository.insert(bb);
    }
    public static boolean checkIDisUnic(UUID u) {
        Cursor<BorrowedBook> cursor = borrowedRepository.find();
        for (BorrowedBook book : cursor) {
            if (u.equals(book.getId())) {
                return false;
            }
        }
        return true;

    }
}
