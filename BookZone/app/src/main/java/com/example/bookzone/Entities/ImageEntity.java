package com.example.bookzone.Entities;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bookzone.Utils.DateConverter;

@Entity(tableName = "Images", foreignKeys = @ForeignKey(entity = BookEntity.class,
        parentColumns = "name",
        childColumns = "book_title",
        onDelete =  ForeignKey.CASCADE))
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idImage")
    private int imageId;

    @ColumnInfo(name = "picture")
    @TypeConverters(DateConverter.class)
    private Uri uriPath;

    @ColumnInfo(name = "book_title")
    private String bookTitle;

    public ImageEntity(Uri uriPath, String bookTitle) {
        this.uriPath = uriPath;
        this.bookTitle = bookTitle;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Uri getUriPath() {
        return uriPath;
    }

    public void setUriPath(Uri uriPath) {
        this.uriPath = uriPath;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
