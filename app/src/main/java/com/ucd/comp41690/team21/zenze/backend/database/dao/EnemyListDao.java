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

    @Query("SELECT * FROM enemylist")
    List<EnemyList> getAll();

    @Query("SELECT * FROM enemylist where id LIKE :id")
    Player findById(int id);

    @Query("SELECT COUNT(*) from enemylist")
    int countEnemyLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(EnemyList... enemyLists);

    @Update
    void update(EnemyList enemyList);

    @Delete
    void delete(EnemyList enemyList);

}
