package com.example.bookzone.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookzone.Entities.ImageEntity;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM Images WHERE book_title = :title")
    List<ImageEntity> getAllImagesForABook(String title);

    @Insert
    void insertImage(ImageEntity image);
}
