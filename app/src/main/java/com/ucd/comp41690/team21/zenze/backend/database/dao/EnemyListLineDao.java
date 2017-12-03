package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyListLine;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface EnemyListLineDao {

    /**
     * Get all enemy list lines from the database
     * @return
     */
    @Query("SELECT * FROM EnemyListLine")
    List<EnemyListLine> getAll();

    /**
     * Get all enemy list lines corresponding to an enemy list from the database
     * @param enemyListId
     * @return
     */
    @Query("SELECT * FROM EnemyListLine WHERE EnemyListId LIKE :enemyListId")
    List<EnemyListLine> getByEnemyListId(int enemyListId);

    /**
     * Get an enemy list line by id
     * @param id
     * @return
     */
    @Query("SELECT * FROM EnemyListLine where id LIKE :id")
    EnemyListLine findById(int id);

    @Query("SELECT COUNT(*) from EnemyListLine WHERE EnemyListId LIKE :enemyListId")
    int countEnemyListLinesInList(int enemyListId);

    /**
     * Insert one or more enemy list lines in the database
     * @param enemyListLines
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(EnemyListLine... enemyListLines);

    /**
     * Update an enemy list line in the database
     * @param enemyListLine
     */
    @Update
    void update(EnemyListLine enemyListLine);

    /**
     * Delete an enemy list line in the database
     * @param enemyListLine
     */
    @Delete
    void delete(EnemyListLine enemyListLine);
}
