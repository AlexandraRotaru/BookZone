package com.example.bookzone.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookzone.Entities.UserEntity;


@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    UserEntity getUser();

    @Insert
    void insertUser(UserEntity user);
}
