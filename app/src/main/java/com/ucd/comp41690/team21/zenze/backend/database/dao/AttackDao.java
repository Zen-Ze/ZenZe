package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.Attack;
import com.ucd.comp41690.team21.zenze.backend.database.models.Enemy;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface AttackDao {

    /**
     * Retrieves all attacks stored in the database
     * @return a list of Attacks
     */
    @Query("SELECT * FROM attack")
    List<Attack> getAll();

    /**
     * Retrieves all attacks stored in the database corresponding to a WeatherStatus
     * @param weatherStatus a weather status
     * @return a list of Attacks
     */
    @Query("SELECT * FROM attack WHERE WeatherStatus LIKE :weatherStatus")
    List<Attack> getByWeatherStatus(int weatherStatus);

    /**
     * Retrieves an attack stored in the database
     * @param id an attack id
     * @return an Attack
     */
    @Query("SELECT * FROM attack where id LIKE :id")
    Attack findById(int id);

    @Query("SELECT COUNT(*) from attack")
    int countAttacks();

    /**
     * Insert one or more attacks in the database
     * @param attacks
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Attack... attacks);

    /**
     * Update an attack in the database
     * @param attack
     */
    @Update
    void update(Attack attack);

    /**
     * Delete an attack in the database
     * @param attack
     */
    @Delete
    void delete(Attack attack);

}
