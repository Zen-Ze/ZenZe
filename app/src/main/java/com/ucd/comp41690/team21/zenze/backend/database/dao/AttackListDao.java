package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 26/11/17.
 */

@Dao
public interface AttackListDao {

    /**
     * Get all attack lists
     * @return
     */
    @Query("SELECT * FROM attacklist")
    List<AttackList> getAll();

    /**
     * Retrieve an attack list by its id
     * @param id
     * @return
     */
    @Query("SELECT * FROM attacklist where id LIKE  :id")
    AttackList findById(int id);

    @Query("SELECT COUNT(*) from attacklist")
    int countAttackLists();

    /**
     * Insert one or more attack lists in the database
     * @param attackLists
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AttackList... attackLists);

    /**
     * Update an attack list in the database
     * @param attackList
     */
    @Update
    void update(AttackList attackList);

    /**
     * Delete an attack list in the database
     * @param attackList
     */
    @Delete
    void delete(AttackList attackList);

}
