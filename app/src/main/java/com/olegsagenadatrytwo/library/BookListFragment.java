package com.olegsagenadatrytwo.library;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookListFragment extends Fragment {

    private static final String TAG = "library";

    private int     administrator;
    private int     numberOfRows;

    private RecyclerView    mBookRecyclerView;
    public  BookAdapter     mAdapter;
    private ProgressDialog  mProgressDialog;

    //this method gets called first but since this is not an activity but a fragment we must not
    //initialize the view here but wait to do so in the onCreateView method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //display Log message
        Log.i(TAG, "BookListFragment onCreate");

            //here we get the intent from the activity that is hosting this fragment
            Intent intent = getActivity().getIntent();

            //administrator variable represents if user is able to change the data or not
            //the information about administrator is passed in the intent and is retrieved from
            //the server during log in
            administrator = intent.getIntExtra("administrator",0);

            //below statement allows for action bar
            setHasOptionsMenu(true);
    }

    //in the onCreateView method you can set the view and other things
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        Log.i(TAG, "BookListFragment onCreateView");

        //inflate the view from book_list and set it to view
        View view = inflater.inflate(R.layout.book_list, container, false);

        //find a RecyclerView inside the view
        mBookRecyclerView = (RecyclerView)view.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //progressDialog to display the progress of getting the books from the server
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading Books");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        //FetchArrays is a class that starts a new thread in the background meaning not in the main
        //thread. This class retrieves information from the server and lets the UI know what to
        //display
        FetchArrays fetchArrays = new FetchArrays(BookLab.get(getActivity()).getBooks());
        fetchArrays.execute();

        Log.i(TAG, "BookListFragment onCreateView end");
        return view;
    }

    //this method updates the user interface if any changes were made
    public void updateUI() {
        //display Log message
        Log.i(TAG, "BookListFragment updateUI ");
        //set the adapter for the recyclerView
        if(mAdapter == null) {
            mAdapter = new BookAdapter(BookLab.get(getActivity()).getBooks());
            mBookRecyclerView.setAdapter(mAdapter);
        }else{
            //updated single item
            // mAdapter.notifyItemChanged(saved);
            //update multiple
            mAdapter.setBooks(BookLab.get(getActivity()).getBooks());
            mAdapter.notifyDataSetChanged();
        }
        Log.i(TAG, "BookListFragment updateUI end");
    }

    //next 3methods are a bad way to perform multithreading because they call each other in a chain
    //like fashion based on different situations. There is a better way to implement this but that's all I know for now.
    public void newBookArrived(){
        FetchArrays fetchArraysnew = new FetchArrays(BookLab.get(getActivity()).getBooks());
        fetchArraysnew.execute();
    }
    public void checkForNewBook(){
        CheckForNewBooksInBackground checkForNewBooksInBackground = new CheckForNewBooksInBackground();
        checkForNewBooksInBackground.execute();
    }
    public void getPictures(){
        GetPicturesInBackground getPicturesInBackground = new GetPicturesInBackground(BookLab.get(getActivity()).getBooks());
        getPicturesInBackground.execute();
    }

    //this method places the menu in the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        Log.i(TAG, "BookListFragment: onCreateOptionsMenu");

        //if the administrator variable is zero than the user will not be able to add new books
        if(administrator == 0) {
            Log.i(TAG, "BookListFragment: onCreateOptionsMenu notAdmin");
            inflater.inflate(R.menu.tool_bar_options, menu);
            SearchView searchView = (SearchView)menu.findItem(R.id.menu_item_search).getActionView();
            SearchManager searchManager =(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

            if (null != searchView) {
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getActivity().getComponentName()));
            }

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    // this is your adapter that will be filtered
                    Log.i(TAG,"onQueryTextChange");
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    Log.i(TAG,"onQueryTextSubmit");
                    search(query);
                    Log.i(TAG,query);
                    return true;
                }
            };
            if (searchView != null) {
                searchView.setOnQueryTextListener(queryTextListener);
            }

        }
        //if the administrator is not zero then the user will be able to add new Books
        else {
            inflater.inflate(R.menu.tool_bar_options_administrator, menu);
            SearchView searchView = (SearchView)menu.findItem(R.id.menu_item_search).getActionView();
            SearchManager searchManager =(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

                        searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getActivity().getComponentName()));

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    // this is your adapter that will be filtered
                    Log.i(TAG,"onQueryTextChange");
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    Log.i(TAG,"onQueryTextSubmit");
                    //Here u can get the value "query" which is entered in the search box.
                    search(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

    }
    public void search(String query){
        int found =0;
        String lowerCaseQuery = query.toLowerCase();
        ArrayList<Book> books = BookLab.get(getActivity()).getBooks();
        for (int i = 0; i<books.size(); i++){
            if(books.get(i).getTitle().toLowerCase().contains(lowerCaseQuery)){
                Book book = books.get(i);
                books.remove(i);
                books.add(0, book);
                found++;
            }
        }
        if(found == 0){
            Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
        }
        updateUI();
    }

    //this method is called if any of the action bar items are clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "BookListFragment: onOptionsItemSelected");

        //switch statement to determine which of the action bar items have been selected
        switch (item.getItemId()){

            //this menu_item_new_book will be available only for administrators
            case R.id.menu_item_new_book:
                Log.i(TAG, "BookListFragment: case: new_new_book");

                //since the menu_item_new_book was clicked we create a new book
                Book book = new Book();

                //here we retrieve the list from the BookLab
                ArrayList<Book> books = BookLab.get(getActivity()).getBooks();
                //here we add the new book to the list we retrieved from the BookLab
                books.add(book);
                //Here we start the BookPagerActivity with details of the book and more
                Intent intent = BookPagerActivity.newIntent(getActivity(),book.getId());
                //here we pass information through intent to the activity that we starting
                intent.putExtra("administrator", administrator);
                startActivity(intent);
                return true;

            case R.id.menu_item_advanced_search:
                Intent intentadvancedsearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentadvancedsearch);
                Log.i(TAG, "BookListFragment: case: search");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "BookListFragment: onResume");
        //after user returns to this Fragment from visiting other activity we must update the UI in case the user made changes
        updateUI();
    }


    //ViewHolder
    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mGenreTextView;
        private ImageView mImageView;
        private Book book;

        public BookHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.book_title_text_view);
            mGenreTextView = (TextView)itemView.findViewById(R.id.book_genre_text_view);
            mImageView      = (ImageView)itemView.findViewById(R.id.image);
        }

        public void bindBook(Book book){
            Log.i(TAG,"BindBook");
            this.book = book;
            mTitleTextView.setText(book.getTitle());
            mGenreTextView.setText(book.getGenre());
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
            Intent intent = BookPagerActivity.newIntent(getActivity(),book.getId());
            intent.putExtra("administrator", administrator);
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.books_first_details, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
            Book book = Books.get(position);
            holder.bindBook(book);
        }

        @Override
        public int getItemCount() {
            return Books.size();
        }

        public void setBooks(List<Book> books){
            Books = books;
        }
    }

    //this class starts a new thread that is not on the main thread and retrieves information
    //from the database server and lets the UI know when to update when ready
    public class FetchArrays extends AsyncTask<Void, Void, ArrayList<Book>> {

        private static final String TAG = "library";
        private ArrayList<Book> myBooks;


        public FetchArrays(ArrayList<Book> myBooks){
            this.myBooks = myBooks;
        }

        //this method starts running in the background then it lets the onPostExecute know when its
        //done
        @Override
        protected ArrayList<Book> doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground");
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                // Construct the URL
                URL url = new URL("http://www.selfiewars-vote.com/GetBooks.php");

                // Create the request, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, line);
                    try {
                        Log.i(TAG, "Fetch: getBooks response try");
                        //create JSONObject to retrieve response from the server
                        JSONObject jsonResponse = new JSONObject(line);
                        Log.i(TAG, "here");
                        //retrieve all the information from json
                        //success is set to true if retrieving was successful in the server (php)
                        boolean success = jsonResponse.getBoolean("success");
                        String numberofRow = jsonResponse.getString("numberofRows");
                        numberOfRows = Integer.parseInt(numberofRow);
                        myBooks.clear();
                        if (success) {
                            Log.i(TAG, "Fetch: getBooks response success");
                            for (int i = 0; i < numberOfRows * 9; i = i + 9) {

                                String BookId = jsonResponse.getString(i + "");
                                String BookISBN = jsonResponse.getString(i + 1 + "");
                                String BookTitle = jsonResponse.getString(i + 2 + "");
                                String BookAuthor = jsonResponse.getString(i + 3 + "");
                                String BookGenre = jsonResponse.getString(i + 4 + "");
                                String BookSynopsis = jsonResponse.getString(i + 5 + "");
                                String Available = jsonResponse.getString(i + 6 + "");
                                String Price = jsonResponse.getString(i + 7 + "");
                                String EncodedImage = jsonResponse.getString(i + 8 + "");

                                Book book = new Book();
                                book.setId(UUID.fromString(BookId));
                                book.setIsbn(BookISBN);
                                book.setTitle(BookTitle);
                                book.setAuthor(BookAuthor);
                                book.setGenre(BookGenre);
                                book.setSynopsis(BookSynopsis);
                                book.setAvailable(Integer.parseInt(Available));
                                book.setPrice(Price);
                                book.setEncodedImage(EncodedImage);
                                Log.i(TAG, EncodedImage);
                                myBooks.add(book);
                            }

                        } else {
                            Log.i(TAG, "Fetch: getBooks response else");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                return null;

            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.i(TAG, "Error closing stream", e);

                    }

                }

            }
            Log.i(TAG, "doInBackground end");
            return myBooks;
        }

        //this method is executed after the doInBackground is done so this is where we update UI
        //since doInBackground finished getting all the information about books
        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            Log.i(TAG, "onPostExecute: " + books.size());
            //here we set the List of BookLab to the list we got in the doInBackground
            BookLab.get(getActivity()).setBookList(books);
            //start get pictures thread
            getPictures();
        }
    }

    //this class will run in background to download the images and display the progress bar
    private class GetPicturesInBackground extends AsyncTask<Void, Integer, Void>{
        ArrayList<Book> list;
        public GetPicturesInBackground(ArrayList<Book> list){
            this.list = list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMax(BookLab.get(getActivity()).getBooks().size());
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground getting images");
            for (int i = 0; i<list.size(); i++) {
                String url = "http://www.selfiewars-vote.com/picture/" + list.get(i).getId() + ".JPG";
                try {
                    URLConnection connection = new URL(url).openConnection();
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
                    list.get(i).setBitmap(bitmap);
                    publishProgress(i+1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "doInBackground getting images end");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "onPostExecute getting images");
            mProgressDialog.hide();
            //we call the updateUI method
            updateUI();
            checkForNewBook();
        }
    }

    private class CheckForNewBooksInBackground extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "library";
        private int newnNumberOfRows;

        public CheckForNewBooksInBackground(){
        }
        //this method starts running in the background then it lets the onPostExecute know when its
        //done
        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "Check for new Books doInBackground");
            while(true) {
                Log.i(TAG, "inside infinite loop");
                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                try {
                    // Construct the URL
                    URL url = new URL("http://www.selfiewars-vote.com/CheckForNewBooks.php");

                    // Create the request, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {

                        try {
                            Log.i(TAG, "try");
                            JSONObject jsonResponse = new JSONObject(line);
                            String numberofRow = jsonResponse.getString("numberofRows");
                            newnNumberOfRows = Integer.parseInt(numberofRow);
                            if(newnNumberOfRows != numberOfRows){
                                Log.i(TAG, "new item");
                                newBookArrived();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.i(TAG, "Error closing stream", e);

                        }

                    }

                }

                if(newnNumberOfRows != numberOfRows){
                    numberOfRows = newnNumberOfRows;
                    break;
                }
                try {
                    Thread.sleep(20*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //this method is executed after the doInBackground is done so this is where we update UI
        //since doInBackground finished getting all the information about books
        @Override
        protected void onPostExecute(Void v) {
        }
    }
}
