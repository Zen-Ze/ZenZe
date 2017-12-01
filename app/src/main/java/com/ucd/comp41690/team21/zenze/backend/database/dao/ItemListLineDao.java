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

    @Query("SELECT * FROM itemlistline")
    List<ItemListLine> getAll();

    @Query("SELECT * FROM itemlistline WHERE ItemListId LIKE :itemListId")
    List<ItemListLine> getByItemListId(int itemListId);

    @Query("SELECT * FROM itemlistline where id LIKE :id")
    ItemListLine findById(int id);

    @Query("SELECT COUNT(*) from itemlistline WHERE ItemListId LIKE :itemListId")
    int countItemListLinesInList(int itemListId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ItemListLine... itemListLines);

    @Update
    void update(ItemListLine itemListLine);

    @Delete
    void delete(ItemListLine itemListLine);
}
