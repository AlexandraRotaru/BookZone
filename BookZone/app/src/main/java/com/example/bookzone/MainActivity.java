package com.example.bookzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookzone.Dao.UserDao;
import com.example.bookzone.Entities.UserEntity;

public class MainActivity extends AppCompatActivity {

    private static final String APP_PREFERENCES_KEY = "com.example.bookzone.preferences";
    private static final String PREFERENCES_KEY_INPUT_FIRSTNAME = "com.example.bookzone.pref_key.INPUT.FIRSTNAME";
    private static final String PREFERENCES_KEY_INPUT_LASTNAME = "om.example.bookzone.pref_key.INPUT.LASTNAME";

    private String firstname_from_user;
    private String lastname_from_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE);

        String firstname_user_existing = sharedPreferences.getString(PREFERENCES_KEY_INPUT_FIRSTNAME, "");
        String lastname_user_existing = sharedPreferences.getString(PREFERENCES_KEY_INPUT_LASTNAME, "");


        if(firstname_user_existing.equals("") || lastname_user_existing.equals("")) {
            getUserInformations();
        } else {
            sendIntentToUserProfileActivity(firstname_user_existing, lastname_user_existing);
        }
    }

    private void getUserInformations() {

        Button goToUserProfileActivity = findViewById(R.id.button_register);

        goToUserProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView firstname = findViewById(R.id.editText_firstname);
                firstname_from_user = firstname.getText().toString().trim();

                TextView lastname = findViewById(R.id.editText_lastname);
                lastname_from_user = lastname.getText().toString().trim();

                if(firstname_from_user.isEmpty() || lastname_from_user.isEmpty()) {

                    Toast.makeText(getBaseContext(), "Completeaza toate campurile!", Toast.LENGTH_SHORT).show();

                } else {

                    UserEntity userEntity = new UserEntity(firstname_from_user, lastname_from_user);
                    insertUserToDB(userEntity);

                    savePreferences();

                    sendIntentToUserProfileActivity(firstname_from_user, lastname_from_user);
                }
            }
        });
    }

    private void insertUserToDB(UserEntity userEntity) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getApplicationContext());
                    UserDao userDao = db.userDao();
                    userDao.insertUser(userEntity);

                } catch (Exception e) {

                    throwToastMessage();

                }
            }
        };

        thread.start();
    }

    private void sendIntentToUserProfileActivity(String firstname, String lastname) {

        Intent intent = new Intent(MainActivity.this, BooksRecyclerActivity.class);

        intent.putExtra(PREFERENCES_KEY_INPUT_FIRSTNAME, firstname);
        intent.putExtra(PREFERENCES_KEY_INPUT_LASTNAME, lastname);

        startActivity(intent);
    }

    private void throwToastMessage() {
        Toast.makeText(getApplicationContext(), "A aparut o eroare. Va rugam sa reveniti.", Toast.LENGTH_SHORT).show();
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PREFERENCES_KEY_INPUT_FIRSTNAME, firstname_from_user);
        editor.putString(PREFERENCES_KEY_INPUT_LASTNAME, lastname_from_user);
        editor.apply();
    }


}