package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 26/11/17.
 */

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player where id LIKE :id")
    Player findById(int id);

    @Query("SELECT COUNT(*) from player")
    int countPlayers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Player... players);

    @Update
    void update(Player player);

    @Delete
    void delete(Player user);

}
