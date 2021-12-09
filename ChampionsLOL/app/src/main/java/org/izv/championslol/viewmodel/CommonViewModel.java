package org.izv.championslol.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.izv.championslol.model.repository.ChampionRepository;

public class CommonViewModel extends ViewModel {

    private Context context;
    private ChampionRepository repository;

    public CommonViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }
}