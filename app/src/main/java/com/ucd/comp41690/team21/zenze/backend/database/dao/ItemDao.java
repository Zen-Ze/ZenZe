package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item WHERE WeatherStatus LIKE :weatherStatus")
    List<Item> getByWeatherStatus(int weatherStatus);

    @Query("SELECT * FROM item where id LIKE :id")
    Item findById(int id);

    @Query("SELECT COUNT(*) from item")
    int countItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item... items);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

}
