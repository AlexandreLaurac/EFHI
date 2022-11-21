package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.efhi.Modele.Seance;

public class SeanceIntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_seance_introduction) ;

        // TEMPORAIRE : transmission d'une valeur pour vérifier que les choses se sont bien passées dans l'activité précédente
        TextView tempDonnee = findViewById (R.id.activity_seance_introduction_TEMP_DONNEE) ;
        Seance seance = ((MonApplication) SeanceIntroductionActivity.this.getApplication()).getSeance() ;
        String contenu = "" + seance.getNbSequences() ;
        tempDonnee.setText(contenu) ;
    }

    public void clicBoutonCommencer (View view) {
        Intent intention = new Intent (SeanceIntroductionActivity.this, SeanceActivity.class) ;
        startActivity (intention) ;
    }
}