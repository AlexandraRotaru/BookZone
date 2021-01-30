package com.example.bookzone.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookzone.Adapters.BookRecyclerViewAdapter;
import com.example.bookzone.BookZoneDatabase;
import com.example.bookzone.Dao.BookDao;
import com.example.bookzone.Dao.UserDao;
import com.example.bookzone.Entities.BookEntity;
import com.example.bookzone.Fragments.LoadImageFragment;
import com.example.bookzone.R;
import com.example.bookzone.Utils.ItemListener;

import java.util.ArrayList;
import java.util.List;


public class BooksRecyclerActivity extends AppCompatActivity implements ItemListener {

    private static final String KEY_TITLE = "com.example.bookzone.key.title";

    private static Button add_book;

    private String firstname;
    private String lastname;

    private BookZoneDatabase db;

    BookRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_recycler);

        db = BookZoneDatabase.getAppDatabase(getApplicationContext());

        recyclerViewSets();

        init();
    }

    public void init() {

        getUserData();

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = firstname + " " + lastname;
        titleFragment.setText(title);

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + getNumberOfBooks();
        subtitleFragment.setText(subtitle);

        add_book = findViewById(R.id.button_addBook);
        addBookMethod();
    }

    public void getUserData() {
        UserDao userDao = db.userDao();

        firstname = userDao.getUser().getFirstname();
        lastname = userDao.getUser().getLastname();

    }

    public void recyclerViewSets() {
        getBooksFromDB();

        RecyclerView bookRecyclerView = findViewById(R.id.recyclerView_books);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new BookRecyclerViewAdapter(this);

        bookRecyclerView.setLayoutManager(layoutManager);
        bookRecyclerView.setAdapter(adapter);
    }

    public void getBooksFromDB() {
        db.bookDao().getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntities) {
                adapter.setBooks(bookEntities);
            }
        });
    }

    public int getNumberOfBooks() {
        return  db.bookDao().numberOfBooks();
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

    public String getBookTitleForAPosition(int position) {
        return db.bookDao().getBookByPosition(position + 1).getBookName();
    }

    @Override
    public void onItemListener(int position) {
        Intent intent = new Intent(this, BookImagesRecyclerActivity.class);
        intent.putExtra(KEY_TITLE, getBookTitleForAPosition(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}