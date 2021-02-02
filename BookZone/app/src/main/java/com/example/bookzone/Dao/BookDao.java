package com.example.bookzone.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookzone.Entities.BookEntity;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Books ORDER BY idBook DESC")
    LiveData<List<BookEntity>> getAllBooks();

    @Query("SELECT * FROM Books WHERE name = :title")
    BookEntity getBook(String title);

    @Query("SELECT * FROM (SELECT * FROM Books ORDER BY idBook DESC LIMIT :position) ORDER BY idBook LIMIT 1")
    BookEntity getBookByPosition(int position);

    @Insert
    void insertBook(BookEntity book);
}
