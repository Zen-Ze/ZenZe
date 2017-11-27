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

    @Query("SELECT * FROM enemy")
    List<Enemy> getAll();

    @Query("SELECT * FROM enemy WHERE WeatherStatus LIKE :weatherStatus")
    List<Enemy> getByWeatherStatus(int weatherStatus);

    @Query("SELECT * FROM enemy where id LIKE :id")
    Enemy findById(int id);

    @Query("SELECT COUNT(*) from enemy")
    int countEnemies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Enemy... ennemies);

    @Update
    void update(Enemy item);

    @Delete
    void delete(Enemy item);

}
