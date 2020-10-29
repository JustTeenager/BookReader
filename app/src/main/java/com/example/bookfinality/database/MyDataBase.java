package com.example.bookfinality.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class MyDataBase {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "number_page")
    private int number_page;
    @ColumnInfo(name = "registration")
    private boolean isRegister;

    public MyDataBase(int number_page, boolean isRegister){
        id=0;
        this.number_page= number_page;
        this.isRegister = isRegister;
    }

    public int getNumber_page() {
        return number_page;
    }

    public void setNumber_page(int number_page) {
        this.number_page = number_page;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
