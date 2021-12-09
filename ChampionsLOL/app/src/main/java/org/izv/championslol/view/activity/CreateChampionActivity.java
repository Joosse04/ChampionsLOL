package org.izv.championslol.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.izv.championslol.R;
import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.Type;
import org.izv.championslol.viewmodel.ChampionViewModel;
import org.izv.championslol.viewmodel.TypeViewModel;

public class CreateChampionActivity extends AppCompatActivity {

    private Champion champion;
    private ChampionViewModel cvm;
    private ImageView ivImage;
    private Spinner spType;
    private EditText etName, etRole, etDifficulty, etUrl;
    private Button btAdd, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_champion);
        initialize();
    }

    private void initialize() {
        ivImage = findViewById(R.id.ivChampion);
        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etRole = findViewById(R.id.etRole);
        etDifficulty = findViewById(R.id.etDifficulty);
        etUrl = findViewById(R.id.etUrl);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);

        etName.setOnFocusChangeListener((v, hasFocus) -> {
            String url;
            if(!hasFocus) {
                if(!etName.getText().toString().isEmpty()) {
                    url = cvm.getUrl(etName.getText().toString());
                    Glide.with(this).load(url).into(ivImage);
                    etUrl.setText(url);
                }
            }
        });
        getViewModel();
        defineBottonsListeners();
    }

    private Champion getChampion() {
        Type type = (Type) spType.getSelectedItem();
        String name = etName.getText().toString().trim();
        String rol = etRole.getText().toString().trim();
        String difficulty = etDifficulty.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        Champion champion = new Champion();
        champion.name = name;
        champion.role = rol;
        champion.difficulty = difficulty;
        champion.url = url;
        champion.idtype = type.id;
        return champion;
    }

    private void addChampion(Champion champion) {
        cvm.insertChampion(champion);
    }

    private void defineBottonsListeners() {
        btAdd.setOnClickListener(v -> {
            Champion champion = getChampion();
            if(champion.isValid()) {
                addChampion(champion);
            } else {
                Toast.makeText(this, "Error, hay algún campo vacío", Toast.LENGTH_LONG).show();
            }
        });

        btCancel.setOnClickListener(v -> {
            Intent intent = new Intent(CreateChampionActivity.this, ChampionActivity.class);
            startActivity(intent);
        });

    }

    private void getViewModel() {
        cvm = new ViewModelProvider(this).get(ChampionViewModel.class);
        cvm.getInsertResults().observe(this, list -> {
            Log.v("xyzyx", "res: " + list);
            if(list.get(0) > 0) {
                Toast.makeText(this, "Se ha añadido el campeón", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error, este campeón ya existe", Toast.LENGTH_LONG).show();
            }
        });

        TypeViewModel tvm = new ViewModelProvider(this).get(TypeViewModel.class);
        tvm.getTypes().observe(this, types -> {
            Type type = new Type();
            type.id = 0;
            type.name = getString(R.string.default_type);
            types.add(0, type);
            ArrayAdapter<Type> adapter =
                    new ArrayAdapter<Type>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            spType.setAdapter(adapter);
        });
    }


}