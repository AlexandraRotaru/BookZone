package com.example.bookzone;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookzone.Dao.UserDao;
import com.example.bookzone.Entities.UserEntity;


public class BooksRecyclerActivity extends AppCompatActivity {

    private static final String PREFERENCES_KEY_INPUT_FIRSTNAME = "com.example.bookzone.pref_key.INPUT.FIRSTNAME";
    private static final String PREFERENCES_KEY_INPUT_LASTNAME = "om.example.bookzone.pref_key.INPUT.LASTNAME";
    private static Button add_book;

    private String firstname;
    private String lastname;
    private int numberOfPictures;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_recycler);

        init();
    }

    public void init() {
        firstname = getIntent().getStringExtra(PREFERENCES_KEY_INPUT_FIRSTNAME);
        lastname = getIntent().getStringExtra(PREFERENCES_KEY_INPUT_LASTNAME);

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = firstname + " " + lastname;
        titleFragment.setText(title);

        getTotalNumberOfPictures();

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + numberOfPictures;
        subtitleFragment.setText(subtitle);

        add_book = findViewById(R.id.button_addBook);
        addBookMethod();
    }

    private void getTotalNumberOfPictures() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
                    UserDao userDao = db.userDao();
                    UserEntity userEntity = userDao.getUser(firstname, lastname);
                    numberOfPictures = userEntity.getPicturesNumber();

                } catch (Exception e) {
                    throwToastMessage();
                }
            }
        };

        thread.start();
    }

    private void throwToastMessage() {
        Toast.makeText(getApplicationContext(), "A intervenit o eroare. Incercati mai tarziu", Toast.LENGTH_SHORT).show();
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