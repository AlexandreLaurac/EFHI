package com.example.efhi.Modele.BDD;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "seance")
public class Seance implements Parcelable {

    // Attributs
    @PrimaryKey(autoGenerate = true) private long id ;
    @ColumnInfo private int tpsPreparation ;
    @ColumnInfo private int nbSequences ;
    @ColumnInfo private int nbCycles ;
    @ColumnInfo private int tpsTravail ;
    @ColumnInfo private int tpsRepos ;
    @ColumnInfo private int tpsReposLong ;
    @ColumnInfo private String categorie ;
    @ColumnInfo private String titre ;

    // Constructeurs
    public Seance() {}

    @Ignore
    public Seance (int tpsPreparation, int nbSequences, int nbCycles, int tpsTravail, int tpsRepos, int tpsReposLong, String categorie, String titre) {
        this.tpsPreparation = tpsPreparation ;
        this.nbSequences = nbSequences ;
        this.nbCycles = nbCycles ;
        this.tpsTravail = tpsTravail ;
        this.tpsRepos = tpsRepos ;
        this.tpsReposLong = tpsReposLong ;
        this.categorie = categorie ;
        this.titre = titre ;
    }

    @Ignore
    protected Seance (Parcel in) {
        tpsPreparation = in.readInt() ;
        nbSequences = in.readInt() ;
        nbCycles = in.readInt() ;
        tpsTravail = in.readInt() ;
        tpsRepos = in.readInt() ;
        tpsReposLong = in.readInt() ;
        categorie = in.readString() ;
        titre = in.readString() ;
    }


    // Getters
    public long getId () {
        return id ;
    }

    public int getTpsPreparation() {
        return tpsPreparation ;
    }

    public int getNbSequences() {
        return nbSequences ;
    }

    public int getNbCycles() {
        return nbCycles ;
    }

    public int getTpsTravail() {
        return tpsTravail ;
    }

    public int getTpsRepos() {
        return tpsRepos ;
    }

    public int getTpsReposLong() {
        return tpsReposLong ;
    }

    public String getCategorie() {
        return categorie ;
    }

    public String getTitre() {
        return titre ;
    }


    // Setters
    public void setId (long id) {
        this.id = id ;
    }

    public void setTpsPreparation (int tpsPreparation) {
        this.tpsPreparation = tpsPreparation ;
    }

    public void setNbSequences (int nbSequences) {
        this.nbSequences = nbSequences ;
    }

    public void setNbCycles (int nbCycles) {
        this.nbCycles = nbCycles ;
    }

    public void setTpsTravail (int tpsTravail) {
        this.tpsTravail = tpsTravail ;
    }

    public void setTpsRepos (int tpsRepos) {
        this.tpsRepos = tpsRepos ;
    }

    public void setTpsReposLong (int tpsReposLong) {
        this.tpsReposLong = tpsReposLong ;
    }

    public void setCategorie (String categorie) {
        this.categorie = categorie ;
    }

    public void setTitre (String titre) {
        this.titre = titre ;
    }


    // Implémentation de l'interface Parcelable
    public static final Creator<Seance> CREATOR = new Creator<>() {
        @Override
        public Seance createFromParcel (Parcel in) {
            return new Seance(in) ;
        }

        @Override
        public Seance[] newArray (int size) {
            return new Seance[size] ;
        }
    } ;

    @Override
    public int describeContents() {
        return 0 ;
    }

    @Override
    public void writeToParcel (Parcel parcel, int i) {
        parcel.writeInt(tpsPreparation) ;
        parcel.writeInt(nbSequences) ;
        parcel.writeInt(nbCycles) ;
        parcel.writeInt(tpsTravail) ;
        parcel.writeInt(tpsRepos) ;
        parcel.writeInt(tpsReposLong) ;
    }
}
