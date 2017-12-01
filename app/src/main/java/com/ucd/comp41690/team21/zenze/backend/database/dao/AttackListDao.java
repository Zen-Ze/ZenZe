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

    @Query("SELECT * FROM attacklist")
    List<AttackList> getAll();

    @Query("SELECT * FROM attacklist where id LIKE  :id")
    AttackList findById(int id);

    @Query("SELECT COUNT(*) from attacklist")
    int countAttackLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AttackList... attackLists);

    @Update
    void update(AttackList attackList);

    @Delete
    void delete(AttackList attackList);

}
