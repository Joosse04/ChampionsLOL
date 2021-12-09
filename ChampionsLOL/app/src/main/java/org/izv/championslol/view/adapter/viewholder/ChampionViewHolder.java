package org.izv.championslol.view.adapter.viewholder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.championslol.R;
import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.view.activity.EditChampionActivity;

public class ChampionViewHolder extends RecyclerView.ViewHolder {

    public Champion champion;
    public ImageView ivChampion;
    public TextView tvName, tvType, tvRole, tvDifficulty, tvUrl;

    public ChampionViewHolder(@NonNull View itemView) {
        super(itemView);
        ivChampion = itemView.findViewById(R.id.ivChampion);
        tvName = itemView.findViewById(R.id.tvName);
        tvType = itemView.findViewById(R.id.tvType);
        tvRole = itemView.findViewById(R.id.tvRole);
        tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
        tvUrl = itemView.findViewById(R.id.tvUrl);

        itemView.setOnClickListener(v -> {
            Log.v("xyzyx", "onclick" + champion.name);
            Intent intent = new Intent(itemView.getContext(), EditChampionActivity.class);
            intent.putExtra("champion", champion);
            itemView.getContext().startActivity(intent);
        });
    }
}
