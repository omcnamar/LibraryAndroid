package com.olegsagenadatrytwo.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class BookPagerActivity extends AppCompatActivity {
    private static final String EXTRA_BOOK_ID = "com.olegsagenadatrytwo.library";
    private static final String TAG = "library";
    private int administrator;
    private ArrayList<Book> mBooks;

    public  static Intent newIntent(Context packageContext, UUID bookId){
        Log.i(TAG, "BookPagerActivity: newIntent");
        Intent intent = new Intent(packageContext,BookPagerActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "BookPagerActivity: onCreate");
        Intent intent = getIntent();
        UUID bookId = (UUID)intent.getSerializableExtra(EXTRA_BOOK_ID);
        administrator = intent.getIntExtra("administrator",0);

        setContentView(R.layout.activity_book_pager);
        //initialize ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_book_pager_view_pager);

        mBooks = BookLab.get(this).getBooks();
        //get Fragment Manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //set Adapter for mViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Log.i(TAG, "BookPagerActivity: getItem");
                Book book = mBooks.get(position);
                return BookFragment.newInstance(book.getId(), administrator);
            }

            @Override
            public int getCount() {
                Log.i(TAG, "BookPagerActivity: getCount " + mBooks.size());
                return mBooks.size();
            }
        });

        for(int i = 0; i<mBooks.size(); i++){
            Log.i(TAG, "BookPagerActivity: for loop");
            if(mBooks.get(i).getId().equals(bookId)){
                Log.i(TAG, "BookPagerActivity: for lop if");
                viewPager.setCurrentItem(i);
                break;
            }
        }
        Log.i(TAG, "BookPagerActivity: end onCreate");
    }
}
