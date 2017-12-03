package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 26/11/17.
 */

@Dao
public interface EnemyListDao {

    /**
     * Retrieve all enemy lists from the database
     * @return
     */
    @Query("SELECT * FROM enemylist")
    List<EnemyList> getAll();

    /**
     * Retrieve an enemy list from the database
     * @param id
     * @return
     */
    @Query("SELECT * FROM enemylist where id LIKE :id")
    EnemyList findById(int id);

    @Query("SELECT COUNT(*) from enemylist")
    int countEnemyLists();

    /**
     * Insert one or more enemy lists in the database
     * @param enemyLists
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(EnemyList... enemyLists);

    /**
     * Update an enemy list in the database
     * @param enemyList
     */
    @Update
    void update(EnemyList enemyList);

    /**
     * Delete an enemy list in the database
     * @param enemyList
     */
    @Delete
    void delete(EnemyList enemyList);

}
