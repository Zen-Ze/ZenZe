package com.ucd.comp41690.team21.zenze.backend.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;

import java.util.List;

/**
 * Created by timothee on 27/11/17.
 */

@Dao
public interface AttackListLineDao {
    @Query("SELECT * FROM AttackListLine")
    List<AttackListLine> getAll();

    @Query("SELECT * FROM AttackListLine WHERE AttackListId LIKE :attackListId")
    List<AttackListLine> getByAttackListId(int attackListId);

    @Query("SELECT * FROM AttackListLine where id LIKE :id")
    AttackListLine findById(int id);

    @Query("SELECT COUNT(*) from AttackListLine WHERE AttackListId LIKE :attackListId")
    int countAttackListLinesInList(int attackListId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AttackListLine... attackListLines);

    @Update
    void update(AttackListLine attackListLine);

    @Delete
    void delete(AttackListLine attackListLine);
}
