package com.example.bookzone.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookzone.Entities.ImageEntity;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM Images WHERE book_title = :title ORDER BY idImage DESC")
    LiveData<List<ImageEntity>> getAllImagesForABook(String title);

    @Query("SELECT * FROM (SELECT * FROM Images WHERE book_title = :title ORDER BY idImage DESC LIMIT :position) ORDER BY idImage LIMIT 1")
    ImageEntity getImage(String title, int position);

    @Query("SELECT COUNT(*) FROM Images WHERE book_title = :title")
    int getNumberOfPic(String title);

    @Insert
    void insertImage(ImageEntity image);
}
