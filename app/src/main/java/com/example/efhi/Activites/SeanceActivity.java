package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.efhi.Modele.Chronometre.Compteur;
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
    private Boolean pause ;
    private Seance seance ;
    private Seance etatSeance ;
    private Compteur compteur ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_seance) ;

        // Récuparation des paramètres de la séance
        seance = ((MonApplication) SeanceActivity.this.getApplication()).getSeance() ;

        // Initialisation de l'état de la séance
        etatSeance = new Seance (0, 0, 0, 0, 0, 0) ;  // les temps sont inutiles pour l'état courant (ils ne changent pas et sont disponibles dans 'seance'), et les numéros de cycle et de séquence sont à 0 (la séance n'a pas commencé)

        // Récupération des vues
        vueCompteur = findViewById(R.id.activity_seance_Compteur) ;
        vueTextePreparation = findViewById (R.id.activity_seance_textePreparation) ;
        vueTexteSequence = findViewById (R.id.activity_seance_texteSequence) ;
        vueTexteCycle = findViewById (R.id.activity_seance_texteCycle) ;
        vueTexteTravail = findViewById (R.id.activity_seance_texteTravail) ;
        vueTexteRepos = findViewById (R.id.activity_seance_texteRepos) ;
        vueTexteReposLong = findViewById (R.id.activity_seance_texteReposLong) ;
        boutonPause = findViewById(R.id.activity_seance_boutonPause) ;

        // Instanciation du compteur et état associé
        compteur = new Compteur (this) ;
        pause = false ;

        // Initialisation des durées
        compteur.addDuree(seance.getTpsPreparation()) ;
        for (int i = 0 ; i<seance.getNbSequences() ; i++) {
            for (int j = 0 ; j<seance.getSequence().getNbCycles()-1 ; j++) {  // -1 pour ne pas avoir de repos court avant un repos long (cas traité hors de la boucle après)
                compteur.addDuree(seance.getSequence().getCycle().getTpsTravail()) ;
                compteur.addDuree(seance.getSequence().getCycle().getTpsRepos()) ;
            }
            compteur.addDuree(seance.getSequence().getCycle().getTpsTravail()) ;
            compteur.addDuree(seance.getSequence().getTpsReposLong());
        }

        // Démarrage des activités sportives
        compteur.declencheActivites() ;
    }

    public void onPauseCompteur (View view) {
        if (!pause) { // le compteur tourne, on veut le mettre en pause
            pause = true ;
            compteur.pause() ;
            String texteBoutonPause = "reprise" ;
            boutonPause.setText(texteBoutonPause) ;
        }
        else {  // le compteur est en pause, on lui fait reprendre son travail
            pause = false ;
            compteur.start(compteur.getUpdatedTime()) ;
            String texteBoutonPause = "pause" ;
            boutonPause.setText(texteBoutonPause) ;
        }
    }

    public void pageSuivante () {
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

    public void affichageInterface (int tour) {
        // Calcul des numéros de cycle et de séquence à partir du tour
        int numSeq = 0 ;
        int numCycle = 0 ;
        if (tour >= 1) {
            int nbCycles = seance.getSequence().getNbCycles() ;
            numSeq = (tour-1) / (2 * nbCycles) + 1 ;
            numCycle = ((tour-1) - 2 * nbCycles * (numSeq - 1)) / 2 + 1 ;
        }

        // Mise à jour de l'état de la séance - UTILE ??
        etatSeance.setNbSequences(numSeq) ;
        etatSeance.getSequence().setNbCycles(numCycle) ;

        // Affichage
        String textePreparation = "Préparation : " + seance.getTpsPreparation() + "s" ;
        vueTextePreparation.setText(textePreparation) ;

        String texteSequence = "Sequence n°" + etatSeance.getNbSequences() + " / " + seance.getNbSequences() ;
        vueTexteSequence.setText(texteSequence) ;

        String texteCycle = "Cycle n°" + etatSeance.getSequence().getNbCycles() + " / " + seance.getSequence().getNbCycles() ;
        vueTexteCycle.setText(texteCycle) ;

        String texteTravail = "Travail : " + seance.getSequence().getCycle().getTpsTravail() + "s" ;
        vueTexteTravail.setText(texteTravail) ;

        String texteRepos = "Repos : " + seance.getSequence().getCycle().getTpsRepos() + "s" ;
        vueTexteRepos.setText(texteRepos) ;

        String texteReposLong = "Repos long : " + seance.getSequence().getTpsReposLong() + "s" ;
        vueTexteReposLong.setText(texteReposLong) ;

    }

}