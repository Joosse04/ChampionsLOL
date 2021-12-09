package org.izv.championslol.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.ChampionType;
import org.izv.championslol.model.entity.Type;

import java.util.List;

@Dao
public interface ChampionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertChampion(Champion... champions);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Champion champion);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertType(Type... types);

    @Update
    int updateChampion(Champion... champions);

    @Update
    int updateType(Type... types);

    @Delete
    int deleteChampion(Champion... champions);

    @Delete
    int deleteType(Type... types);

    @Query("delete from championtype")
    int deleteAllTypes();

    @Query("delete from Champion")
    int deleteAllChampion();

    @Query("select * from Champion order by name asc")
    LiveData<List<Champion>> getChampions();

    @Query("select p.*, pt.id type_id, pt.name type_name from Champion p join championtype pt on p.idtype = pt.id order by name asc")
    LiveData<List<ChampionType>> getAllChampion();

    @Query("select * from championtype order by name asc")
    LiveData<List<Type>> getTypes();

    @Query("select * from Champion where id = :id")
    LiveData<Champion> getChampion(long id);

    @Query("select * from championtype where id = :id")
    LiveData<Type> getType(long id);

    @Query("select id from championtype where name = :name")
    long getIdType(String name);

    @Query("select * from championtype where name = :name")
    Type getType(String name);
}