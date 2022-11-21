package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoixSeanceDefinitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_definition) ;
    }

    public void clicBoutonValider (View view) {
        Intent intention = new Intent (ChoixSeanceDefinitionActivity.this, SeanceIntroductionActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonEnregistrer (View view) {
        // Enregistrement d'un preset : vérifier que les paramètres chargés sont tous différents de ceux d'un preset existant
        // Afficher un toast dans les deux cas "enregistrement de preset" et "preset déjà existant
    }
}