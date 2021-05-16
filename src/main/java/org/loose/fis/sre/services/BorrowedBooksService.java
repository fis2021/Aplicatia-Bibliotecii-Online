package org.loose.fis.sre.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.model.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class BorrowedBooksService {
    public static ObjectRepository<BorrowedBook> borrowedRepository;
    private static boolean isOk=true;
    public static void initDatabase() {

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("borbooks.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        borrowedRepository = database.getRepository(BorrowedBook.class);
    }


    public static void addBorrowedBook(User u, Book b)
    {    BorrowedBook bb=new BorrowedBook(b, u);

        UUID uid=bb.getId();
        while (checkIDisUnic(uid) == false) {
            uid = bb.rando();
            checkIDisUnic(uid);
        }
        borrowedRepository.insert(bb);

    }
    public static void setasreturned(BorrowedBook b) {

       b.setReturned(true);
       borrowedRepository.update(b);

    }
    public static void setasreturnedd(Book b,Client c) {

        String id_username= SelectedClient.selectedClient.getUsername();
        Cursor<BorrowedBook> cb=borrowedRepository.find();

        for(BorrowedBook bc:cb) {
            if (b.getBook_id().equals(bc.getB().getBook_id())) {
                System.out.println("Am fost aici");
                if (bc.getU().getUsername().equals(id_username)) {
                    System.out.println("Am fost aici2");
                    bc.setReturned(true);
                    borrowedRepository.update(bc);
                }

            }
        }
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
    public static int  BorrowedBooksForOne(User u)
    {   int c=0;
        Cursor<BorrowedBook> cb=borrowedRepository.find();
        for(BorrowedBook bc:cb)
        {
            if(bc.getU().getUsername().equals(u.getUsername()))
            {
                c++;
            }
        }
        return c;
    }
    public static boolean ALready3Books(User u)
    {
        if(BorrowedBooksService.BorrowedBooksForOne(u)>=3)
            return false;
        else
            return true;
    }

    public static boolean checkIfThisBookIsBorrowedByThaSameUser(User u,Book b)
    {
        Cursor<BorrowedBook> cb=borrowedRepository.find();
        for(BorrowedBook bc:cb)
        {
            if(u.getUsername().equals(bc.getU().getUsername()))
            {
                if(b.getBook_id().equals(bc.getB().getBook_id()))
                {
                    return false;
                }
            }
        }
        return true;

    }
    public static boolean CheckIfThisBookStockIsOk(Book b)
    {
        if(b.getStoc()==0)
            return false;
        else
            return true;

    }
    public static boolean getOk()
    {
        return isOk;
    }
    public static ArrayList<Book> borrowedOnesandNotReturnedForSelectedClient()
    {   String id_username= SelectedClient.selectedClient.getUsername();
        Cursor<BorrowedBook> cb=borrowedRepository.find();
        ArrayList<Book> ar=new ArrayList<Book>();
        for(BorrowedBook bc:cb)
        {
            if(bc.getU().getUsername().equals(id_username))
            {   if(bc.isReturned()==false) {
                ar.add(bc.getB());
            }

            }
        }

        return ar;
    }
    public static ArrayList<Book> borrowedOnesandReturnedForSelectedClient()
    {   String id_username= SelectedClient.selectedClient.getUsername();
        Cursor<BorrowedBook> cb=borrowedRepository.find();
        ArrayList<Book> ar=new ArrayList<Book>();
        for(BorrowedBook bc:cb)
        {
            if(bc.getU().getUsername().equals(id_username))
            {   if(bc.isReturned()==true) {
                ar.add(bc.getB());
            }

            }
        }

        return ar;
    }



}
