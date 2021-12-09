package org.izv.championslol.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.championslol.R;
import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.ChampionType;
import org.izv.championslol.view.adapter.ChampionAdapter;
import org.izv.championslol.viewmodel.ChampionViewModel;

import java.util.List;

public class ChampionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        //lista de campeones
        RecyclerView rvChampion = findViewById(R.id.rvChampions);
        rvChampion.setLayoutManager(new LinearLayoutManager(this));

        ChampionViewModel cvm = new ViewModelProvider(this).get(ChampionViewModel.class);
        ChampionAdapter championAdapter = new ChampionAdapter(this);

        rvChampion.setAdapter(championAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(rvChampion.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(rvChampion.getContext(), R.drawable.backgroundchampion));

        //LiveData<List<Champion>> listChampion = cvm.getChampions();
        LiveData<List<ChampionType>> listChampionType = cvm.getAllChampions();
        listChampionType.observe(this, champions -> {
            championAdapter.setChampionList(champions);
        });

        FloatingActionButton fab = findViewById(R.id.fabAddChampion);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateChampionActivity.class);
            startActivity(intent);
        });
    }

}