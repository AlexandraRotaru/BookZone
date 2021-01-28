package com.example.bookzone.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookzone.Entities.BookEntity;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Books")
    List<BookEntity> getAllBooks();


    @Query("SELECT * FROM Books WHERE name = :title")
    BookEntity getBook(String title);

    @Insert
    void insertBook(BookEntity book);
}
