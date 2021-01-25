package com.example.bookzone.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "User")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUser")
    private int userId;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "pictures")
    private int picturesNumber;


    public UserEntity(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.picturesNumber = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPicturesNumber() {
        return picturesNumber;
    }

    public void setPicturesNumber(int picturesNumber) {
        this.picturesNumber = picturesNumber;
    }
}