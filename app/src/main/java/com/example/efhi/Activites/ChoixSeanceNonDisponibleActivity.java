package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.efhi.R;


public class ChoixSeanceNonDisponibleActivity extends AppCompatActivity {

    // Attributs de classe
    public static final String TITRE = "titre" ;
    public static final String EXPLICATION = "explication" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_choix_seance_non_disponible) ;

        // Titre
        TextView vueTexteTitre = findViewById (R.id.activity_choix_seance_non_disponible_titre) ;
        vueTexteTitre.setText(getIntent().getStringExtra(TITRE)) ;

        // Explication
        TextView vueTexteDescription = findViewById (R.id.activity_choix_seance_non_disponible_description) ;
        vueTexteDescription.setText(getIntent().getStringExtra(EXPLICATION)) ;
    }

    public void clicBoutonRetour(View view) {
        finish() ;
    }
}