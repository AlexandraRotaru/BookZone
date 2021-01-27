//package com.example.bookzone.Utils;
//
//import android.net.Uri;
//
//import androidx.room.TypeConverter;
//
//public class DateConverter {
//
//    @TypeConverter
//    public static Uri toUri(String string) {
//        return string == null ? null : Uri.parse(string);
//    }
//
//    @TypeConverter
//    public static String toString(Uri uri) {
//        return uri == null ? null : uri.toString();
//    }
//}
