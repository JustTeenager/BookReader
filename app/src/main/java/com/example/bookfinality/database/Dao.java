
package com.example.bookfinality.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertDataBase(MyDataBase dataBase);

    @Update
    void updateDataBase(MyDataBase dataBase);


    @Query("SELECT*FROM data")
    MyDataBase getMyDataBase();

}