package com.example.bookzone;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

//import com.example.bookzone.Dao.BookDao;
import com.example.bookzone.Dao.UserDao;
//import com.example.bookzone.Entities.BookEntity;
import com.example.bookzone.Entities.UserEntity;



@Database(entities = {UserEntity.class}, version = 1)
public abstract class BookZoneDatabase extends RoomDatabase {

    private static BookZoneDatabase INSTANCE;

    public abstract UserDao userDao();
//    public abstract BookDao bookDao();

    public static BookZoneDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BookZoneDatabase.class,
                    "bookZone-db")
                    .build();
        }

        return INSTANCE;
    }
}
