package org.izv.championslol.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import org.izv.championslol.R;
import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.Type;
import org.izv.championslol.viewmodel.ChampionViewModel;
import org.izv.championslol.viewmodel.TypeViewModel;

public class EditChampionActivity extends AppCompatActivity {

    private Champion champion;
    private ChampionViewModel cvm;
    private ImageView ivChampion;
    private Spinner spType;
    private EditText etName, etRole, etDifficulty, etUrl;
    private Button btEdit, btDelete, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_champion);
        initialize();
    }

    private void initialize() {
        champion = getIntent().getExtras().getParcelable("champion");
        Log.v("xyzyx", champion.name);

        ivChampion = findViewById(R.id.ivChampion);
        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etRole = findViewById(R.id.etRole);
        etDifficulty = findViewById(R.id.etDifficulty);
        etUrl = findViewById(R.id.etUrl);
        btEdit = findViewById(R.id.btEdit);
        btDelete = findViewById(R.id.btDelete);
        btCancel = findViewById(R.id.btCancel);

        Glide.with(this).load(champion.url).into(ivChampion);
        etName.setText(champion.name);
        etRole.setText(champion.role);
        etDifficulty.setText(champion.difficulty);
        etUrl.setText(champion.url);

        ChampionViewModel cvm = new ViewModelProvider(this).get(ChampionViewModel.class);
        TypeViewModel tvm = new ViewModelProvider(this).get(TypeViewModel.class);
        tvm.getTypes().observe(this, types -> {
            Type type = new Type();
            type.id = 0;
            type.name = getString(R.string.default_type);
            types.add(0, type);
            ArrayAdapter<Type> adapter =
                    new ArrayAdapter<Type>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            spType.setAdapter(adapter);
            spType.setSelection((int) champion.idtype);
        });

        btEdit.setOnClickListener(v -> {
            Champion champion = getChampion();
            if (champion.isValid()){
                cvm.updateChampion(champion);
                finish();
            }
        });

        btDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder  = new AlertDialog.Builder(this);
            builder.setTitle("ESTAS A PUNTO DE BORRAR ESTE CAMPEÓN")
                    .setMessage("¿Estas seguro de que deseas borrarlo?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Si clickea cancel, lo deja en la actividad en la que estaba
                        }
                    })
                    .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Champion champion = getChampion();
                            if (champion.isValid()){
                                cvm.deleteChampion(champion);
                                finish();
                            }
                        }
                    });
            builder.create().show();
        });

        btCancel.setOnClickListener(v -> {
            Intent intent = new Intent(EditChampionActivity.this, ChampionActivity.class);
            startActivity(intent);
        });
    }

    private Champion getChampion() {
        Type type = (Type) spType.getSelectedItem();
        String name = etName.getText().toString().trim();
        String rol = etRole.getText().toString().trim();
        String difficulty = etDifficulty.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        Champion champion = new Champion();
        champion.id = this.champion.id;
        champion.name = name;
        champion.idtype = type.id;
        champion.role = rol;
        champion.difficulty = difficulty;
        champion.url = url;
        return champion;
    }
}