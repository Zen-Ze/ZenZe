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

    /**
     * Retrieve a list of items from the database
     * @return a list of items
     */
    @Query("SELECT * FROM item")
    List<Item> getAll();

    /**
     * Retrieve a list of items from the database by weather status
     * @param weatherStatus a weather status
     * @return a list of items
     */
    @Query("SELECT * FROM item WHERE WeatherStatus LIKE :weatherStatus")
    List<Item> getByWeatherStatus(int weatherStatus);

    /**
     * Retrieve an item from the database by id
     * @return an item
     */
    @Query("SELECT * FROM item where id LIKE :id")
    Item findById(int id);

    @Query("SELECT COUNT(*) from item")
    int countItems();

    /**
     * Insert one or more items in the database
     * @param items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item... items);

    /**
     * Update an item in the database
     * @param item
     */
    @Update
    void update(Item item);

    /**
     * Delete an item in the database
     * @param item
     */
    @Delete
    void delete(Item item);

}
