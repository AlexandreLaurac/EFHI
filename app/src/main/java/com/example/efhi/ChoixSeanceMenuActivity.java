package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoixSeanceMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_menu) ;

        // Ici, il faudra rendre invisibles certains boutons en fonction de l'existence ou non d'éléments dans la base, et de séance choisie précédemment ou non
    }

    public void clicBoutonDerniereSeance (View view) {
        // Soit récupération des données de la dernière séance ici, et transmission à l'activité suivante
        // Soit pas de transmission et récupération des valeurs dans l'activité suivante
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceDefinitionActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonSeancesEnregistrees (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceEnregistreesActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonPresets (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeancePresetsActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonDefinirSeance (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceDefinitionActivity.class) ;
        startActivity (intention) ;
    }
}