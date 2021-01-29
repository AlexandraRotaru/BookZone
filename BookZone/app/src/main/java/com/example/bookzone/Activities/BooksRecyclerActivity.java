package com.example.bookzone.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookzone.Adapters.BookRecyclerViewAdapter;
import com.example.bookzone.BookZoneDatabase;
import com.example.bookzone.Dao.BookDao;
import com.example.bookzone.Dao.UserDao;
import com.example.bookzone.Entities.BookEntity;
import com.example.bookzone.Entities.UserEntity;
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

    private List<BookEntity> allBooks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_recycler);

        recyclerViewSets();

        init();
    }

    public void init() {

        getUserData();

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = firstname + " " + lastname;
        titleFragment.setText(title);

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + allBooks.size();
        subtitleFragment.setText(subtitle);

        add_book = findViewById(R.id.button_addBook);
        addBookMethod();
    }

    public void getUserData() {
        BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        firstname = userDao.getUser().getFirstname();
        lastname = userDao.getUser().getLastname();

    }

    public void recyclerViewSets() {
        getBooksFromDB();

        RecyclerView bookRecyclerView = findViewById(R.id.recyclerView_books);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(this, allBooks, this);

        bookRecyclerView.setLayoutManager(layoutManager);
        bookRecyclerView.setAdapter(adapter);
    }

    public void getBooksFromDB() {
        allBooks = new ArrayList<>();

        BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
        BookDao bookDao = db.bookDao();

        allBooks = bookDao.getAllBooks();
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

    @Override
    public void onItemListener(int position) {
        Intent intent = new Intent(this, BookImagesRecyclerActivity.class);
        intent.putExtra(KEY_TITLE, allBooks.get(position).getBookName());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}