package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.efhi.Modele.Chronometre.Compteur;
import com.example.efhi.Modele.Donnees.EtatSeance;
import com.example.efhi.Modele.Donnees.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;


public class SeanceActivity extends AppCompatActivity implements Declencheur {

    // Attributs
        // Vues
    private TextView vueCompteur ;
    private TextView vueTextePreparation ;
    private TextView vueTexteSequence ;
    private TextView vueTexteCycle ;
    private TextView vueTexteTravail ;
    private TextView vueTexteRepos ;
    private TextView vueTexteReposLong ;
    private Button boutonPause ;
        // "Donnees"
    private Seance seance ;
    private EtatSeance etatSeance ;
    private Compteur compteur ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;

        // Récupération des vues
        setContentView(R.layout.activity_seance) ;
        vueCompteur = findViewById(R.id.activity_seance_Compteur) ;
        vueTextePreparation = findViewById (R.id.activity_seance_textePreparation) ;
        vueTexteSequence = findViewById (R.id.activity_seance_texteSequence) ;
        vueTexteCycle = findViewById (R.id.activity_seance_texteCycle) ;
        vueTexteTravail = findViewById (R.id.activity_seance_texteTravail) ;
        vueTexteRepos = findViewById (R.id.activity_seance_texteRepos) ;
        vueTexteReposLong = findViewById (R.id.activity_seance_texteReposLong) ;
        boutonPause = findViewById(R.id.activity_seance_boutonPause) ;

        // Récuparation (des paramètres) de la séance
        seance = ((MonApplication) SeanceActivity.this.getApplication()).getSeance() ;

        // Initialisation de l'état de la séance
        etatSeance = new EtatSeance (seance) ;

        // Instanciation du compteur
        compteur = new Compteur (this, true) ;

        // Initialisation des durées -- EN FAIRE UNE METHODE A PART - une méthode ici ou une méthode de DeclencheActivitesEntrainement ? Plutôt ici car la précédente est générique et ne sais pas les temps que ses déclencheurs possèdent
        compteur.addDuree(seance.getTpsPreparation()) ;
        for (int i = 0 ; i<seance.getNbSequences() ; i++) {
            for (int j = 0 ; j<seance.getNbCycles()-1 ; j++) {  // -1 pour ne pas avoir de repos court avant un repos long (cas traité hors de la boucle après)
                compteur.addDuree(seance.getTpsTravail()) ;
                compteur.addDuree(seance.getTpsRepos()) ;
            }
            compteur.addDuree(seance.getTpsTravail()) ;
            compteur.addDuree(seance.getTpsReposLong());
        }

        // Démarrage des activités sportives
        compteur.declencheActivites() ;
    }

    public void onPauseCompteur (View view) {
        if (!compteur.getEnPause()) {  // le compteur tourne, on veut le mettre en pause
            compteur.pause() ;
            String texteBoutonPause = "reprise" ;
            boutonPause.setText(texteBoutonPause) ;
        }
        else {  // le compteur est en pause, on lui fait reprendre son travail
            compteur.start(compteur.getUpdatedTime()) ;
            String texteBoutonPause = "pause" ;
            boutonPause.setText(texteBoutonPause) ;
        }
    }

    private void affichageEtatSeance() {

        if (etatSeance.estEnPreparation()) {  // on est à l'activité de préparation

            // TextView de l'activité de préparation
            String textePreparation = "Préparation : " + seance.getTpsPreparation() + "s" ;
            vueTextePreparation.setText(textePreparation) ;
            vueTextePreparation.setTextColor(Color.BLACK) ;

            // TextView des numéros de séquence et de cycle
            String texteSequence = "Sequence" ;
            vueTexteSequence.setText(texteSequence) ;
            vueTexteSequence.setTextColor(Color.LTGRAY) ;

            String texteCycle = "Cycle" ;
            vueTexteCycle.setText(texteCycle) ;
            vueTexteCycle.setTextColor(Color.LTGRAY) ;

            // TextView des autres activités
            String texteTravail = "Travail : " + seance.getTpsTravail() + "s" ;
            vueTexteTravail.setText(texteTravail) ;
            vueTexteTravail.setTextColor(Color.LTGRAY) ;

            String texteRepos = "Repos : " + seance.getTpsRepos() + "s" ;
            vueTexteRepos.setText(texteRepos) ;
            vueTexteRepos.setTextColor(Color.LTGRAY) ;

            String texteReposLong = "Repos long : " + seance.getTpsReposLong() + "s" ;
            vueTexteReposLong.setText(texteReposLong) ;
            vueTexteReposLong.setTextColor(Color.LTGRAY) ;
        }
        else {  // On est dans une séquence

            // TextView de l'activité de préparation en gris
            vueTextePreparation.setTextColor(Color.LTGRAY) ;

            // TextView des numéros de cycles et de séquences
            String texteSequence = "Sequence n°" + etatSeance.getNumSeq() + " / " + seance.getNbSequences() ;
            vueTexteSequence.setText(texteSequence) ;
            vueTexteSequence.setTextColor(Color.GRAY) ;

            String texteCycle = "Cycle n°" + etatSeance.getNumCycle() + " / " + seance.getNbCycles() ;
            vueTexteCycle.setText(texteCycle) ;
            vueTexteCycle.setTextColor(Color.GRAY) ;

            // TextView des activités (travail, repos, ou repos long) en noir ou gris selon l'activité en cours
            if (etatSeance.estEnTravail()) {  // activité "travail"
                vueTexteTravail.setTextColor(Color.BLACK) ;
                vueTexteRepos.setTextColor(Color.GRAY) ;
                vueTexteReposLong.setTextColor(Color.GRAY) ;
            }
            else if (etatSeance.estEnRepos()) {  // activité "repos"
                vueTexteTravail.setTextColor(Color.GRAY);
                vueTexteRepos.setTextColor(Color.BLACK);
                vueTexteReposLong.setTextColor(Color.GRAY);
            }
            else if (etatSeance.estEnReposLong()) {  // activité "repos long"
                vueTexteTravail.setTextColor(Color.GRAY) ;
                vueTexteRepos.setTextColor(Color.GRAY) ;
                vueTexteReposLong.setTextColor(Color.BLACK) ;
            }
        }
    }

    private void pageSuivante() {
        Intent intention = new Intent (SeanceActivity.this, SeanceConclusionActivity.class) ;
        startActivity (intention) ;
    }


    // Méthodes de l'interface Declencheur

    public void declenche (int duree) {
        compteur.start((long) duree * 1000) ;
    }

    public void termine() {
        pageSuivante() ;
    }

    public void miseAJourCompteur() {
        // Affichage des informations du compteur
        String texteCompteur = "" + compteur.getMinutes() + ":"
                + String.format("%02d", compteur.getSecondes()) + ":"
                + String.format("%03d", compteur.getMillisecondes()) ;
        vueCompteur.setText(texteCompteur) ;
    }

    public void affichageSeance (int tour) {
        etatSeance.setEtatSeance(tour) ;  // Mise à jour de l'état de la séance
        affichageEtatSeance() ;  // Affichage
    }

}