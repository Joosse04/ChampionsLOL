package org.izv.championslol.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "champion",
        indices = {@Index(value = "name", unique = true)},
        foreignKeys = {@ForeignKey(entity = Type.class, parentColumns = "id", childColumns = "idtype", onDelete = ForeignKey.CASCADE)})
public class Champion implements Parcelable {

    //id autonumérico
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idtype")
    public long idtype;

    @ColumnInfo(name = "role")
    public String role;

    @ColumnInfo(name = "difficulty")
    public String difficulty;

    @ColumnInfo(name = "url")
    public String url;

    public Champion() {

    }

    protected Champion(Parcel in) {
        id = in.readLong();
        name = in.readString();
        idtype = in.readLong();
        role = in.readString();
        difficulty = in.readString();
        url = in.readString();
    }

    public static final Creator<Champion> CREATOR = new Creator<Champion>() {
        @Override
        public Champion createFromParcel(Parcel in) {
            return new Champion(in);
        }

        @Override
        public Champion[] newArray(int size) {
            return new Champion[size];
        }
    };

    public boolean isValid() {
        return !(name.isEmpty() || idtype <= 0 || role.isEmpty() || difficulty.isEmpty() || url.isEmpty());
        //shortcut
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idtype);
        dest.writeString(role);
        dest.writeString(difficulty);
        dest.writeString(url);
    }
}