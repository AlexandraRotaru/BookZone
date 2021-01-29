package com.example.bookzone.Entities;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bookzone.Utils.DateConverter;

@Entity(tableName = "Books", indices = {@Index(value = {"name"}, unique = true)})
public class BookEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBook")
    private int bookId;

    @ColumnInfo(name = "name")
    private String bookName;

    @ColumnInfo(name = "picture")
    @TypeConverters(DateConverter.class)
    private Uri uriPath;

    public BookEntity(String bookName, Uri uriPath) {
        this.bookName = bookName;
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

    public Uri getUriPath() {
        return uriPath;
    }

    public void setUriPath(Uri uriPath) {
        this.uriPath = uriPath;
    }
}
