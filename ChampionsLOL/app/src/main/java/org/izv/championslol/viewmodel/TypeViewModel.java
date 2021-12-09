package org.izv.championslol.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.championslol.model.entity.Type;
import org.izv.championslol.model.repository.ChampionRepository;

import java.util.List;

public class TypeViewModel extends AndroidViewModel {

    private ChampionRepository repository;

    public TypeViewModel(@NonNull Application application) {
        super(application);
        repository = new ChampionRepository(application);
    }

    public void insertType(Type... types) {
        repository.insertType(types);
    }

    public void updateType(Type... types) {
        repository.updateType(types);
    }

    public void deleteType(Type... types) {
        repository.deleteType(types);
    }

    public LiveData<List<Type>> getTypes() {
        return repository.getTypes();
    }

    public LiveData<Type> getType(long id) {
        return repository.getType(id);
    }
}
