package org.izv.championslol.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.ChampionType;
import org.izv.championslol.model.entity.Type;
import org.izv.championslol.model.repository.ChampionRepository;

import java.util.List;

public class ChampionViewModel extends AndroidViewModel {

    private ChampionRepository repository;

    public ChampionViewModel(@NonNull Application application) {
        super(application);
        repository = new ChampionRepository(application);
    }

    public void insertChampion(Champion... champions) {
        repository.insertChampion(champions);
    }

    public void updateChampion(Champion... champions) {
        repository.updateChampion(champions);
    }

    public void deleteChampion(Champion... champions) {
        repository.deleteChampion(champions);
    }

    public LiveData<List<Champion>> getChampions() {
        return repository.getChampions();
    }

    public LiveData<Champion> getChampion(long id) {
        return repository.getChampion(id);
    }

    public void insertChampion(Champion champion, Type type) {
        repository.insertChampion(champion, type);
    }

    public LiveData<List<ChampionType>> getAllChampions() {
        return repository.getAllChampions();
    }

    public MutableLiveData<Long> getInsertResult() {
        return repository.getInsertResult();
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return repository.getInsertResults();
    }

    public String getUrl(String championName) {
        return repository.getUrl(championName);
    }
}
