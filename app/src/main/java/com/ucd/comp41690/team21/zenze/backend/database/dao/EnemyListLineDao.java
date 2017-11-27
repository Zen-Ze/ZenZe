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
    @Query("SELECT * FROM EnemyListLine")
    List<EnemyListLine> getAll();

    @Query("SELECT * FROM EnemyListLine WHERE EnemyListId LIKE :enemyListId")
    List<EnemyListLine> getByEnemyListId(int enemyListId);

    @Query("SELECT * FROM EnemyListLine where id LIKE :id")
    EnemyListLine findById(int id);

    @Query("SELECT COUNT(*) from EnemyListLine WHERE EnemyListId LIKE :enemyListId")
    int countEnemyListLinesInList(int enemyListId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(EnemyListLine... enemyListLines);

    @Update
    void update(EnemyListLine enemyListLine);

    @Delete
    void delete(EnemyListLine enemyListLine);
}
