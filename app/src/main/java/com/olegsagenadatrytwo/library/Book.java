package com.olegsagenadatrytwo.library;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

public class Book implements Serializable{
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String price;
    private UUID id;
    private String synopsis;
    private int available;
    private String encodedImage;
    private Bitmap mBitmap;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }
    public Book(){
        title    = "";
        author   = "";
        isbn     = "";
        genre    = "";
        synopsis = "";
        price    = "";
        id     = UUID.randomUUID();
    }
    public Book(Book book){
        title = book.getTitle();
        author = book.getAuthor();
        isbn = book.getIsbn();
        genre = book.getGenre();
        synopsis = book.getSynopsis();
        price = book.getPrice();
        id = book.getId();
        encodedImage = book.getEncodedImage();

    }
    //set and get title
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    //set and get author
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return author;
    }

    //get and set ISBN
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    public String getIsbn(){
        return isbn;
    }

    //get and set genre
    public void setGenre(String genre){
        this.genre = genre;
    }
    public String getGenre(){
        return genre;
    }

    //get id
    public UUID getId(){
        return id;
    }
    public void setId(UUID id){
        this.id = id;
    }

    //get and set synopsis
    public void setSynopsis(String synopsis){
        this.synopsis = synopsis;
    }
    public String getSynopsis(){
        return synopsis;
    }

    //get and set available
    public void setAvailable(int available){
        this.available = available;
    }
    public int getAvailable(){
        return available;
    }

    //get and set the encoded Image String
    public void setEncodedImage(String encodedImage){
        this.encodedImage = encodedImage;
    }
    public String getEncodedImage(){
        return encodedImage;
    }

    public void setBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
    }
    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return price;
    }
}
