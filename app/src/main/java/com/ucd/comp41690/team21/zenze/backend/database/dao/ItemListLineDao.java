package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface ItemListLineDao {

    /**
     * Retrieves a list of item list lines in the database
     * @return a list of item list lines
     */
    @Query("SELECT * FROM itemlistline")
    List<ItemListLine> getAll();

    /**
     * Retrieves an item list line from the database
     * @param itemListId the item list id
     * @return an item list line
     */
    @Query("SELECT * FROM itemlistline WHERE ItemListId LIKE :itemListId")
    List<ItemListLine> getByItemListId(int itemListId);

    /**
     * Retrieves an item list line from the database
     * @param id the item list line id
     * @return an item list line
     */
    @Query("SELECT * FROM itemlistline where id LIKE :id")
    ItemListLine findById(int id);

    @Query("SELECT COUNT(*) from itemlistline WHERE ItemListId LIKE :itemListId")
    int countItemListLinesInList(int itemListId);

    /**
     * Insert one or more item list lines in the database
     * @param itemListLines
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ItemListLine... itemListLines);

    /**
     * Update an item list line in the database
     * @param itemListLine
     */
    @Update
    void update(ItemListLine itemListLine);

    /**
     * Delete an item list line in the database
     * @param itemListLine
     */
    @Delete
    void delete(ItemListLine itemListLine);
}
