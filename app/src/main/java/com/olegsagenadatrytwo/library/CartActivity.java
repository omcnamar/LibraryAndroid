package com.olegsagenadatrytwo.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CartActivity extends AppCompatActivity {

    private static final String TAG = "library";

    private RecyclerView mBookRecyclerView;
    private int saved;
    public  BookAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mBookRecyclerView = (RecyclerView)findViewById(R.id.book_recycler_view_cart);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    public void updateUI() {
        //display Log message
        Log.i(TAG, "BookListFragment updateUI ");
        //set the adapter for the recyclerView
        if(mAdapter == null) {
            Log.i(TAG, "BookListFragment updateUI inside if");
            mAdapter = new BookAdapter(Cart.get().getBooks());
            mBookRecyclerView.setAdapter(mAdapter);
        }else{
            Log.i(TAG, "BookListFragment updateUI else");
            //updated single item
            // mAdapter.notifyItemChanged(saved);
            //update multiple
            mAdapter.setBooks(Cart.get().getBooks());
            mAdapter.notifyDataSetChanged();
        }

        Log.i(TAG, "BookListFragment updateUI end");
    }



    //ViewHolder
    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mPrice;
        private TextView mGenreTextView;
        private ImageView mImageView;
        private TextView mTitleTextView;
        private Book book;
        private Button button;
        private double totalPrice;

        public BookHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mPrice = (TextView)itemView.findViewById(R.id.cart_book_price);
            mGenreTextView = (TextView)itemView.findViewById(R.id.book_genre_text_view_cart);
            mImageView      = (ImageView)itemView.findViewById(R.id.cart_image);
            mTitleTextView  = (TextView)itemView.findViewById(R.id.book_title_text_view_cart);
            button          = (Button)itemView.findViewById(R.id.button_test);
            if(Cart.get().getBooks().size() != 0){
                totalPrice =0;
                for(int i = 0;i<Cart.get().getBooks().size(); i++){
                    totalPrice += Double.parseDouble(Cart.get().getBooks().get(i).getPrice());
                }
            }
        }

        public void bindBook(Book book){
            Log.i(TAG,"BindBook");
            this.book = book;
            mPrice.setText(book.getPrice());
            mGenreTextView.setText(book.getGenre());
            mTitleTextView.setText(book.getTitle());
            if(book.getBitmap()==null) {
                Log.i(TAG,"BindBook ==null");
                mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_book_picture_default));
            }
            else{
                Log.i(TAG,"BindBook else");
                mImageView.setImageBitmap(book.getBitmap());
            }
            Log.i(TAG,"BindBook end");
        }

        public void onClick(View v){
            saved = getAdapterPosition();
            Intent intent = BookPagerActivity.newIntent(getApplicationContext(),book.getId());
            intent.putExtra("administrator", 0);
            startActivity(intent);
        }
    }

    //Adapter
    private class BookAdapter extends RecyclerView.Adapter<BookHolder>{
        private List<Book> Books;

        public BookAdapter(List<Book> books){
            Books = books;
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(CartActivity.this);
//            View view = layoutInflater.inflate(R.layout.cart_book_details, parent, false);
//            return new BookHolder(view);
            View itemView;
            if(viewType == R.layout.cart_book_details){
                LayoutInflater layoutInflater = LayoutInflater.from(CartActivity.this);
                itemView = layoutInflater.inflate(R.layout.cart_book_details, parent, false);
            }

            else {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
            }

            return new BookHolder(itemView);

        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
//            Book book = Books.get(position);
//            holder.bindBook(book);
            if(position == Books.size()) {
                holder.button.setText("Total cost:  $" + holder.totalPrice+"");
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Book book = Books.get(position);
                holder.bindBook(book);
            }
        }

        @Override
        public int getItemCount() {
            return Books.size()+1;
        }
        @Override
        public int getItemViewType(int position) {
            return (position == Books.size()) ? R.layout.button : R.layout.cart_book_details;
        }

        public void setBooks(List<Book> books){
            Books = books;
        }
    }
}