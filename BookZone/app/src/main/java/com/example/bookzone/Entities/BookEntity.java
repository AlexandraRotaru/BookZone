package com.example.bookzone.Entities;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bookzone.Utils.DateConverter;

@Entity(tableName = "Books")
public class BookEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBook")
    private int bookId;

    @ColumnInfo(name = "name")
    private String bookName;

    @ColumnInfo(name = "picturesNumber")
    private int picturesNumber;

    @ColumnInfo(name = "picture")
    @TypeConverters(DateConverter.class)
    private Uri uriPath;

    public BookEntity(String bookName, Uri uriPath) {
        this.bookName = bookName;
        this.picturesNumber = 0;
        this.uriPath = uriPath;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPicturesNumber() {
        return picturesNumber;
    }

    public void setPicturesNumber(int picturesNumber) {
        this.picturesNumber = picturesNumber;
    }

    public Uri getUriPath() {
        return uriPath;
    }

    public void setUriPath(Uri uriPath) {
        this.uriPath = uriPath;
    }
}
