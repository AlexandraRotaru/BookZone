package com.example.bookzone.Dao;

import android.net.Uri;

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

    @Query("SELECT * FROM Images WHERE idImage = :position")
    ImageEntity getImage(int position);

    @Insert
    void insertImage(ImageEntity image);
}
