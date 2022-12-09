package com.example.efhi.Activites;

import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ChoixSeanceAffichageActivity extends AppCompatActivity {

    // Attributs
    private TextView vueTexteTitre ;
    private TextView vueTextePreparation ;
    private TextView vueTexteSequences ;
    private TextView vueTexteCycles ;
    private TextView vueTexteTravail ;
    private TextView vueTexteRepos ;
    private TextView vueTexteReposLong ;
    private Seance seance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_choix_seance_affichage) ;

        // Récuparation (des paramètres) de la (dernière) séance
        seance = ((MonApplication) this.getApplication()).getSeance() ;

        // Récupération des TextView
        vueTexteTitre = findViewById (R.id.activity_choix_seance_affichage_titre) ;
        vueTextePreparation = findViewById (R.id.activity_choix_seance_affichage_tpsPreparation) ;
        vueTexteSequences = findViewById (R.id.activity_choix_seance_affichage_nbSequences) ;
        vueTexteCycles = findViewById (R.id.activity_choix_seance_affichage_nbCycles) ;
        vueTexteTravail = findViewById (R.id.activity_choix_seance_affichage_tpsTravail) ;
        vueTexteRepos = findViewById (R.id.activity_choix_seance_affichage_tpsRepos) ;
        vueTexteReposLong = findViewById (R.id.activity_choix_seance_affichage_tpsReposLong) ;

        // Mise à jour du texte des vues Texte en fonction des valeurs de 'seance'
        String titre = seance.getTitre().isEmpty() ? "Seance" : seance.getTitre() ;
        vueTexteTitre.setText(titre) ;
        String textePreparation = "" + seance.getTpsPreparation() ;
        vueTextePreparation.setText(textePreparation) ;
        String texteSequences = "" + seance.getNbSequences() ;
        vueTexteSequences.setText(texteSequences) ;
        String texteCycles = "" + seance.getNbCycles() ;
        vueTexteCycles.setText(texteCycles) ;
        String texteTravail = "" + seance.getTpsTravail() ;
        vueTexteTravail.setText(texteTravail) ;
        String texteRepos = "" + seance.getTpsRepos() ;
        vueTexteRepos.setText(texteRepos) ;
        String texteReposLong = "" + seance.getTpsReposLong() ;
        vueTexteReposLong.setText(texteReposLong) ;
    }


    public void clicBoutonValider (View view) {
        Intent intention = new Intent (ChoixSeanceAffichageActivity.this, SeanceIntroductionActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonModifier (View view) {
        // Modifier la vue pour pouvoir entrer de nouvelles valeurs (changer les textview de valeurs en EditText)
    }
}