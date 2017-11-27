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

    @Query("SELECT * FROM attack")
    List<Attack> getAll();

    @Query("SELECT * FROM attack WHERE WeatherStatus LIKE :weatherStatus")
    List<Attack> getByWeatherStatus(int weatherStatus);

    @Query("SELECT * FROM attack where id LIKE :id")
    Attack findById(int id);

    @Query("SELECT COUNT(*) from attack")
    int countAttacks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Attack... attacks);

    @Update
    void update(Attack attack);

    @Delete
    void delete(Attack attack);

}
