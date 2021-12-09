package org.izv.championslol.model.entity;

import androidx.room.Embedded;

public class ChampionType {

    @Embedded
    public Champion champion;

    @Embedded(prefix="type_")
    public Type type;
}
