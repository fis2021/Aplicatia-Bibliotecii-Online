package org.loose.fis.sre.model;

import org.dizitart.no2.objects.Id;

import java.util.UUID;

public class BorrowedBook {
    @Id
    private UUID id;
    private Book b;
    private User u;
    private boolean returned=false;
    public BorrowedBook(Book b,User u)
    {  this.id =UUID.randomUUID();
        this.b=b;
        this.u=u;
    }
    public UUID rando()
    {
        this.id =UUID.randomUUID();
        return this.id;

    }
    public Book getB() {
        return b;
    }

    public void setB(Book b) {
        this.b = b;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
