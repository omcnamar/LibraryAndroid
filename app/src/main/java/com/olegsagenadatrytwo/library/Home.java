package com.olegsagenadatrytwo.library;

import android.support.v4.app.Fragment;
import android.util.Log;

//This Activity will be hosting a Fragment which is BookListFragment
//inorder to do so, instead of having an onCreate method I extend the SingleFragmentActivity class
//which I have created to simplify code cluster, and which holds a single fragment
public class Home extends SingleFragmentActivity {
    private static final String TAG = "library";

    //this method will return a BookListFragment
    //so all the computations and GUI from this point is in the BookListFragment class
    @Override
    protected Fragment createFragment() {
        //display Log message at the start of the method
        Log.i(TAG, "Home createFragment");

        //create and initialize BookListFragment
        BookListFragment bookListFragment = new BookListFragment();

        //Display Log message at the end of the method
        Log.i(TAG, "Home createFragment end");

        //return fragment
        return bookListFragment;
    }
}
