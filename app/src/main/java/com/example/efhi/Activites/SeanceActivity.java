package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.Chronometre.Compteur;
import com.example.efhi.Modele.Donnees.EtatSeance;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SeanceActivity extends AppCompatActivity implements Declencheur {

    // Attributs statiques
    public static final String TOUR = "tour" ;
    public static final String EN_PAUSE = "pause" ;
    public static final String UPDATED_TIME = "update" ;
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static { System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ; LOGGER.setLevel(Level.INFO) ; }


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
        setContentView (R.layout.activity_seance) ;
        recuperationDesVues() ;

        // Récuparation (des paramètres) de la séance
        seance = ((MonApplication) SeanceActivity.this.getApplication()).getSeance() ;

        // Initialisation de l'état de la séance
        etatSeance = new EtatSeance (seance) ;

        // Instanciation du compteur
        compteur = new Compteur (this, true) ;

        // Initialisation des durées
        initialisationDesDurees() ;

        // Lancement des activités en fonction du savedInstanceState
        lancementActivitesSportives (savedInstanceState) ;
    }

    private void recuperationDesVues() {
        vueCompteur = findViewById (R.id.activity_seance_Compteur) ;
        vueTextePreparation = findViewById (R.id.activity_seance_textePreparation) ;
        vueTexteSequence = findViewById (R.id.activity_seance_texteSequence) ;
        vueTexteCycle = findViewById (R.id.activity_seance_texteCycle) ;
        vueTexteTravail = findViewById (R.id.activity_seance_texteTravail) ;
        vueTexteRepos = findViewById (R.id.activity_seance_texteRepos) ;
        vueTexteReposLong = findViewById (R.id.activity_seance_texteReposLong) ;
        boutonPause = findViewById (R.id.activity_seance_boutonPause) ;
    }

    private void initialisationDesDurees() {
        compteur.addDuree(seance.getTpsPreparation()) ;
        for (int i = 0 ; i<seance.getNbSequences() ; i++) {
            for (int j = 0 ; j<seance.getNbCycles()-1 ; j++) {  // -1 pour ne pas avoir de repos court avant un repos long (cas traité hors de la boucle après)
                compteur.addDuree(seance.getTpsTravail()) ;
                compteur.addDuree(seance.getTpsRepos()) ;
            }
            compteur.addDuree(seance.getTpsTravail()) ;
            compteur.addDuree(seance.getTpsReposLong()) ;
        }
    }

    public void lancementActivitesSportives (Bundle savedInstanceState) {

        // Un état a été sauvegardé, on met à jour l'état de la séance et le compteur en fonction de cet état précédent
        if (savedInstanceState != null) {

            // Actions à mener concernant l'état de la séance
            compteur.setTour(savedInstanceState.getInt(TOUR)) ;
            affichageSeance(compteur.getTour()) ;  // met à jour l'attribut etatSeance et l'affiche

            // Actions à mener concernant le compteur
            compteur.setUpdatedTime(savedInstanceState.getLong(UPDATED_TIME)) ;
            compteur.setEnPause(savedInstanceState.getBoolean(EN_PAUSE)) ;
            if (compteur.getEnPause()) {  // le chronomètre était en pause
                compteur.setEnPause(false) ; // on fait passer artificiellement le compteur à l'état en marche pour appeler onPauseCOmpteur et qu'elle effectue la bonne opération
                onPauseCompteur (boutonPause) ;
            }
            else {  // le chronomètre tournait, on le fait reprendre là où il en était
                compteur.start(compteur.getUpdatedTime()) ;
            }
        }
        // Aucun état n'a été sauvegardé - on commence l'activité
        else {
            compteur.declencheActivites() ;
        }
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {

        // Sauvegarde des attributs de compteur suffisant pour régénérer l'interface
        savedInstanceState.putInt(TOUR, compteur.getTour()) ;
        savedInstanceState.putBoolean(EN_PAUSE, compteur.getEnPause()) ;
        savedInstanceState.putLong(UPDATED_TIME, compteur.getUpdatedTime()) ;

        // On met le compteur en pause s'il ne l'est pas, pour éviter la poursuite des décomptes de l'instance de compteur de cette activité
        if (!compteur.getEnPause()) {
            compteur.pause() ;
        }

        // "Always call the superclass so it can save the view hierarchy state"
        super.onSaveInstanceState(savedInstanceState) ;
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

        // Variables locales
        int couleurPreparation, couleurSequence, couleurCycle, couleurTravail, couleurRepos, couleurReposLong ;
        String texteSequence, texteCycle ;

        // 1. Affectation de valeurs aux différentes variables locales

        // Etat Présentation
        if (etatSeance.estEnPreparation()) {
            texteSequence = "Sequence n°0 / " + seance.getNbSequences() ;
            texteCycle = "Cycle n°0 / " + seance.getNbCycles() ;
            couleurPreparation = Color.BLACK ;
            couleurSequence = Color.LTGRAY ;
            couleurCycle = Color.LTGRAY ;
            couleurTravail = Color.LTGRAY ;
            couleurRepos = Color.LTGRAY ;
            couleurReposLong = Color.LTGRAY ;
        }
        // Etat Séquence (== ! Etat Présentation)
        else {
            texteSequence = "Séquence n°" + etatSeance.getNumSeq() + " / " + seance.getNbSequences() ;
            texteCycle = "Cycle n°" + etatSeance.getNumCycle() + " / " + seance.getNbCycles() ;
            couleurPreparation = Color.LTGRAY ;
            couleurSequence = Color.GRAY ;
            couleurCycle = Color.GRAY ;
            // Etat Travail
            if (etatSeance.estEnTravail()) {
                couleurTravail = Color.BLACK ;
                couleurRepos = Color.LTGRAY ;
                couleurReposLong = Color.LTGRAY ;
            }
            // Etat Repos
            else if (etatSeance.estEnRepos()) {
                couleurTravail = Color.LTGRAY ;
                couleurRepos = Color.BLACK ;
                couleurReposLong = Color.LTGRAY ;
            }
            // Etat Repos Long
            else {
                couleurTravail = Color.LTGRAY ;
                couleurRepos = Color.LTGRAY ;
                couleurReposLong = Color.BLACK ;
            }
        }

        // 2. Attribution des variables locales aux TextViews

        // TextView de préparation
        String textePreparation = "Préparation : " + seance.getTpsPreparation() + "s" ;
        vueTextePreparation.setText(textePreparation) ;
        vueTextePreparation.setTextColor(couleurPreparation) ;

        // TextView de séquence
        vueTexteSequence.setText(texteSequence) ;
        vueTexteSequence.setTextColor(couleurSequence) ;

        // TextView de cycle
        vueTexteCycle.setText(texteCycle) ;
        vueTexteCycle.setTextColor(couleurCycle) ;

        // TextView de travail
        String texteTravail = "Travail : " + seance.getTpsTravail() + "s" ;
        vueTexteTravail.setText(texteTravail) ;
        vueTexteTravail.setTextColor(couleurTravail) ;

        // TextView de repos
        String texteRepos = "Repos : " + seance.getTpsRepos() + "s" ;
        vueTexteRepos.setText(texteRepos) ;
        vueTexteRepos.setTextColor(couleurRepos) ;

        // TextView de repos long
        String texteReposLong = "Repos long : " + seance.getTpsReposLong() + "s" ;
        vueTexteReposLong.setText(texteReposLong) ;
        vueTexteReposLong.setTextColor(couleurReposLong) ;
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
