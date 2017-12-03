package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 26/11/17.
 */

@Dao
public interface ItemListDao {

    /**
     * Retrieves items lists from the database
     * @return a list of item lists
     */
    @Query("SELECT * FROM itemlist")
    List<ItemList> getAll();

    /**
     * Retrieve an item list from the database
     * @param id the id of the item list
     * @return an Item List
     */
    @Query("SELECT * FROM itemlist where id LIKE :id")
    ItemList findById(int id);

    @Query("SELECT COUNT(*) from itemlist")
    int countItemLists();

    /**
     * Insert one or more item list in the database
     * @param itemLists
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ItemList... itemLists);

    /**
     * Update an item list in the database
     * @param itemList
     */
    @Update
    void update(ItemList itemList);

    /**
     * Delete an item list in the database
     * @param itemList
     */
    @Delete
    void delete(ItemList itemList);
    
}
