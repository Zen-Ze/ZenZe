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

    @Query("SELECT * FROM itemlist")
    List<ItemList> getAll();

    @Query("SELECT * FROM itemlist where id LIKE :id")
    Player findById(int id);

    @Query("SELECT COUNT(*) from itemlist")
    int countItemLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ItemList... itemLists);

    @Update
    void update(ItemList itemList);

    @Delete
    void delete(ItemList itemList);
    
}
