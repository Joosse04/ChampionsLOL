package org.izv.championslol.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.ChampionType;
import org.izv.championslol.model.entity.Type;
import org.izv.championslol.model.room.ChampionDao;
import org.izv.championslol.model.room.ChampionDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ChampionRepository {

    private static final String INIT = "init";

    private HashMap<String, String> championMap;
    private ChampionDao dao;
    private SharedPreferences preferences;
    private LiveData<List<ChampionType>> allChampions;
    private LiveData<List<Champion>> liveChampions;
    private LiveData<Champion> liveChampion;
    private LiveData<List<Type>> liveTypes;
    private LiveData<Type> liveType;
    private MutableLiveData<List<Long>> liveInsertResults;
    private MutableLiveData<Long> liveInsertResult;

    public ChampionRepository(Context context) {
        championMap = new HashMap<>();
        ChampionDatabase db = ChampionDatabase.getDatabase(context);
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        liveInsertResults = new MutableLiveData<>();
        liveInsertResult = new MutableLiveData<>();
        if(!getInit()) {
            typesPreload();
            setInit();
        }
    }

    public void insertChampion(Champion champion, Type type) {
        Runnable r = () -> {
            champion.idtype = insertTypeGetId(type);
            if(champion.idtype > 0) {
                dao.insertChampion(champion);
            }
        };
        new Thread(r).start();
    }

    public MutableLiveData<Long> getInsertResult() {
        return liveInsertResult;
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return liveInsertResults;
    }

    private long insertTypeGetId(Type type) {
        List<Long> ids = dao.insertType(type);
        if(ids.get(0) < 1) {
            return dao.getIdType(type.name);
        } else {
            return ids.get(0);
        }
    }

    public void insertChampion(Champion... champions) {
        Runnable r = () -> {
            List<Long> resultList = dao.insertChampion(champions);
            liveInsertResult.postValue(resultList.get(0));
            liveInsertResults.postValue(resultList);
        };
        new Thread(r).start();
    }

    public void insertType(Type... types) {
        Runnable r = () -> {
            dao.insertType(types);
        };
        new Thread(r).start();
    }

    public void updateChampion(Champion... champions) {
        Runnable r = () -> {
            dao.updateChampion(champions);
        };
        new Thread(r).start();
    }

    public void updateType(Type... types) {
        Runnable r = () -> {
            dao.updateType(types);
        };
        new Thread(r).start();
    }

    public void deleteChampion(Champion... champions) {
        Runnable r = () -> {
            dao.deleteChampion(champions);
        };
        new Thread(r).start();
    }

    public void deleteType(Type... types) {
        Runnable r = () -> {
            dao.deleteType(types);
        };
        new Thread(r).start();
    }

    public LiveData<List<Champion>> getChampions() {
        if(liveChampions == null) {
            liveChampions = dao.getChampions();
        }
        return liveChampions;
    }

    public LiveData<List<Type>> getTypes() {
        if(liveTypes == null) {
            liveTypes = dao.getTypes();
        }
        return liveTypes;
    }

    public LiveData<Champion> getChampion(long id) {
        if(liveChampion == null) {
            liveChampion = dao.getChampion(id);
        }
        return liveChampion;
    }

    public LiveData<Type> getType(long id) {
        if(liveType == null) {
            liveType = dao.getType(id);
        }
        return liveType;
    }

    public LiveData<List<ChampionType>> getAllChampions() {
        if(allChampions == null) {
            allChampions = dao.getAllChampion();
        }
        return allChampions;
    }

    public void typesPreload() {
        String[] typeNames = new String[] {"Assassin", "Fighter", "Shooter", "Support", "Tank", "Wizard"};
        Type[] types = new Type[typeNames.length];
        Type type;
        int cont = 0;
        for (String s: typeNames) {
            type = new Type();
            type.name = s;
            types[cont] = type;
            cont++;
        }
        insertType(types);
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }

    public String getUrl(String championName) {
        String url = championMap.get(championName.toLowerCase());
        if(url == null) {
            // Url de campeon no encontrado
            url = "https://thumbs.dreamstime.com/b/icono-de-plantilla-error-lugar-muerto-p%C3%A1gina-no-encontrada-problemas-con-el-sistema-eps-164583533.jpg";
        }
        return url;
    }

    private void populateHashMap(String string) {
        String name, url;
        try {
            JSONArray jsonArray = new JSONArray(string);
            JSONObject jsonObject;
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name").toLowerCase();
                url = jsonObject.getString("ThumbnailImage");
                championMap.put(name, url);
            }
        } catch (JSONException e) {
        }
    }
}