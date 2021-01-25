//package com.example.bookzone.Entities;
//
//import android.widget.ImageView;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "Books")
//public class BookEntity {
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "idBook")
//    private int bookId;
//
//    @ColumnInfo(name = "name")
//    private String bookName;
//
//    @ColumnInfo(name = "picturesNumber")
//    private int picturesNumber;
//
////    @ColumnInfo(name = "picture")
////    private ImageView imageOfBook;
//
//    public BookEntity(String bookName) {
//        this.bookName = bookName;
//        this.picturesNumber = 0;
////        this.imageOfBook = imageOfBook;
//    }
//
//    public int getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(int bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getBookName() {
//        return bookName;
//    }
//
//    public void setBookName(String bookName) {
//        this.bookName = bookName;
//    }
//
//    public int getPicturesNumber() {
//        return picturesNumber;
//    }
//
//    public void setPicturesNumber(int picturesNumber) {
//        this.picturesNumber = picturesNumber;
//    }
//
////    public ImageView getImageOfBook() {
////        return imageOfBook;
////    }
////
////    public void setImageOfBook(ImageView imageOfBook) {
////        this.imageOfBook = imageOfBook;
////    }
//}
