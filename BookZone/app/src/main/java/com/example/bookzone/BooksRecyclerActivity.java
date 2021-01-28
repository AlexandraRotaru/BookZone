package com.example.bookzone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookzone.Adapters.BookRecyclerViewAdapter;
import com.example.bookzone.Dao.BookDao;
import com.example.bookzone.Entities.BookEntity;

import java.util.ArrayList;
import java.util.List;


public class BooksRecyclerActivity extends AppCompatActivity {

    private static final String PREFERENCES_KEY_INPUT_FIRSTNAME = "com.example.bookzone.pref_key.INPUT.FIRSTNAME";
    private static final String PREFERENCES_KEY_INPUT_LASTNAME = "om.example.bookzone.pref_key.INPUT.LASTNAME";
    private static Button add_book;

    private String firstname;
    private String lastname;
    private int numberOfPictures = 0;

    private List<BookEntity> allBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_recycler);

        recyclerViewSets();

        init();
    }

    public void init() {
        firstname = getIntent().getStringExtra(PREFERENCES_KEY_INPUT_FIRSTNAME);
        lastname = getIntent().getStringExtra(PREFERENCES_KEY_INPUT_LASTNAME);

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = firstname + " " + lastname;
        titleFragment.setText(title);

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + numberOfPictures;
        subtitleFragment.setText(subtitle);

        add_book = findViewById(R.id.button_addBook);
        addBookMethod();
    }

    public void recyclerViewSets() {
        getBooksFromDB();

        RecyclerView bookRecyclerView = findViewById(R.id.recyclerView_books);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(this, allBooks);

        bookRecyclerView.setLayoutManager(layoutManager);
        bookRecyclerView.setAdapter(adapter);
    }


    public void getBooksFromDB() {
        allBooks = new ArrayList<>();

        BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
        BookDao bookDao = db.bookDao();

        allBooks = bookDao.getAllBooks();

//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
//                    BookDao bookDao = db.bookDao();
//
//                    allBooks = bookDao.getAllBooks();
//
//                } catch (Exception e) {
//                    throw e;
//                }
//            }
//        };
//
//        thread.start();
    }


    private void addBookMethod() {
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_book.setVisibility(View.INVISIBLE);

                LoadImageFragment loadImageFragment = new LoadImageFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout_fragment_load_image, loadImageFragment)
                        .commit();

            }
        });
    }

    public static void setAdd_book() {
        add_book.setVisibility(View.VISIBLE);
    }
}