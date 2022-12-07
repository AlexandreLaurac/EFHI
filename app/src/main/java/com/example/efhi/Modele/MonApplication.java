package com.example.efhi.Modele;

import android.app.Application ;
import com.example.efhi.Modele.BDD.Seance;


public class MonApplication extends Application {

    // Informations à conserver
    private Seance seance ;
    private boolean lastExists ;  // booléen indiquant l'existence dans la base d'un enregistrement de la précédente séance

    // Getters
    public Seance getSeance() {
        return seance ;
    }

    public boolean getLastExists() {
        return lastExists ;
    }

    // Setters
    public void setSeance (Seance seance) {
        this.seance = seance ;
    }

    public void setLastExists (boolean lastExists) {
        this.lastExists = lastExists ;
    }
}
