package com.example.efhi.Modele;

import android.app.Application ;
import com.example.efhi.Modele.Donnees.Seance;

public class MonApplication extends Application {

    // Information à conserver
    private Seance seance ;

    // Getter, setter
    public Seance getSeance() {
        return seance ;
    }
    public void setSeance (Seance seance) {
        this.seance = seance ;
    }
}
