package com.olegsagenadatrytwo.library;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;
import com.olegsagenadatrytwo.library.BookDbSchema.BookTable;
//this class will hold the list of books and will pass them from one activity to another
//you can only create an instance of this class once with a method get, and then any other class that tries to
//create an instance will get the same instance that was created at first. This is done by making
//the constructor private and creating a get method that returns an instance of this class
public class BookLab{

    private static final String TAG = "library";

    //create private variable
    private static BookLab booklab;
    private Context context;
    private ArrayList<Book> mBooks;

    //this method returns an Instance of this class
    public static BookLab get(Context context){
        if(booklab == null){
            booklab = new BookLab(context);
        }
        return booklab;
    }

    //private constructor
    private BookLab(Context context){
        mBooks = new ArrayList<>();
        context = context.getApplicationContext();
    }
    //returns the list of Books
    public ArrayList<Book> getBooks(){
        Log.i(TAG, "BookLab: getBooks");
        return mBooks;
    }
    //sets Book list
    public void setBookList(ArrayList<Book> list){
        Log.i(TAG, "setBookList");
        mBooks = list;
    }
    //remove book from the list
    public void removeBook(UUID id){
        for(int i = 0; i<mBooks.size(); i++){
            if(mBooks.get(i).getId().equals(id)){
                mBooks.remove(i);
            }
        }
    }

}
