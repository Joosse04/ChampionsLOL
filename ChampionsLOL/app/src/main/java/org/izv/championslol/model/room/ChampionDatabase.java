package org.izv.championslol.model.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.Type;

@Database(entities = {Champion.class, Type.class}, version = 1, exportSchema = false)
public abstract class ChampionDatabase extends RoomDatabase {

    public abstract ChampionDao getDao();

    private static volatile ChampionDatabase INSTANCE;

    public static ChampionDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ChampionDatabase.class, "champion").build();
        }
        return INSTANCE;
    }

}