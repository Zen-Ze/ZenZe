package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.Enemy;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface EnemyDao {
    /**
     * Retrieve all enemies from the database
     * @return
     */
    @Query("SELECT * FROM enemy")
    List<Enemy> getAll();

    /**
     * Retrieve all enemies from the database by weather status
     * @param weatherStatus
     * @return
     */
    @Query("SELECT * FROM enemy WHERE WeatherStatus LIKE :weatherStatus")
    List<Enemy> getByWeatherStatus(int weatherStatus);

    /**
     * Retrieve an enemy from the database by id
     * @param id
     * @return
     */
    @Query("SELECT * FROM enemy where id LIKE :id")
    Enemy findById(int id);

    @Query("SELECT COUNT(*) from enemy")
    int countEnemies();

    /**
     * Insert one or more ennemies in the database
     * @param ennemies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Enemy... ennemies);

    /**
     * Update an enemy in the database
     * @param enemy
     */
    @Update
    void update(Enemy enemy);

    /**
     * Delete an enemy in the database
     * @param enemy
     */
    @Delete
    void delete(Enemy enemy);

}
