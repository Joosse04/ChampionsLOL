package org.izv.championslol.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.izv.championslol.R;
import org.izv.championslol.model.entity.Champion;
import org.izv.championslol.model.entity.ChampionType;
import org.izv.championslol.model.entity.Type;
import org.izv.championslol.view.adapter.viewholder.ChampionViewHolder;

import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionViewHolder> {

    private List<ChampionType> championList;
    private Context context;

    public ChampionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ChampionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_champion, parent, false);
        return new ChampionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionViewHolder holder, int position) {
        ChampionType championType = championList.get(position);
        Champion champion = championType.champion;
        holder.champion = champion;
        Type type = championType.type;
        holder.tvName.setText(champion.name);
        holder.tvType.setText(type.name);
        holder.tvRole.setText(champion.role);
        holder.tvDifficulty.setText(champion.difficulty);
        holder.tvUrl.setText(champion.url);
        Glide.with(context).load(champion.url).into(holder.ivChampion);
    }

    @Override
    public int getItemCount() {
        if(championList == null) {
            return 0;
        }
        return championList.size();
    }

    public void setChampionList(List<ChampionType> championList) {
        this.championList = championList;
        notifyDataSetChanged();
    }
}
