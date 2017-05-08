package com.olegsagenadatrytwo.library;

import java.util.ArrayList;

/**
 * Created by omcna on 11/16/2016.
 */
public class Cart {
    private ArrayList<Book> mBooks;
    private static Cart sCart;

    //this method returns an Instance of this class
    public static Cart get(){
        if(sCart == null){
            sCart = new Cart();
        }
        return sCart;
    }

    private Cart(){
        mBooks = new ArrayList<>();
    }
    public void setBooks(ArrayList<Book> books) {
        mBooks = books;
    }
    public ArrayList<Book> getBooks() {
        return mBooks;
    }
    public void addBookToCart(Book book){
        mBooks.add(book);
    }
}
