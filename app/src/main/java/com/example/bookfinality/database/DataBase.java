package com.example.bookfinality.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyDataBase.class},version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract Dao dao();

}