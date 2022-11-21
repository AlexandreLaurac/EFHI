package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SeanceConclusionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_seance_conclusion) ;
    }

    public void clicBoutonRecommencer (View view) {
        // Revenir à la page du menu de choix de séance
        // Pour cela supprimer toutes les activités de la pile au-dessus de celle-ci
    }

    public void clicBoutonQuitter (View view) {
        // Quitter l'application - ou alors emmener vers une dernière vue qui dit au revoir à l'utilisateur et s'éteint toute seule ?
    }
}
