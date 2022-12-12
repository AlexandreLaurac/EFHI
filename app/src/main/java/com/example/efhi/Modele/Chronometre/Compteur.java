package com.example.efhi.Modele.Chronometre;

import android.os.CountDownTimer;

import com.example.efhi.Activites.Declencheur;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fbm on 24/10/2017.
 * Modified for project EFHI by AL
 */


public class Compteur extends DeclencheActivitesEntrainement {

    // Attribut de classe
    private static final int COUNT_DOWN_INTERVAL = 10 ;
    private static final long TEMPS_REPRISE = 2000 ;
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static { System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ; LOGGER.setLevel(Level.INFO) ; }

    // Attributs
    private CountDownTimer timer ;
    private long updatedTime ;
    private boolean enPause ;
    //private boolean sonRepriseDejaJoue ;
    private int secondesEcoulees ;

    // Constructeur
    public Compteur (Declencheur declencheur) {
        super (declencheur) ;
        updatedTime = 0 ;
        this.enPause = true ;
        secondesEcoulees = 0 ;
    }

    // Getters
    public long getUpdatedTime() {
        return updatedTime ;
    }

    public boolean getEnPause() {
        return enPause ;
    }

    public int getMinutes() {
        return (int) (updatedTime / 1000)/60;
    }

    public int getSecondes() {
        int secs = (int) (updatedTime / 1000);
        return secs % 60;
    }

    public int getMillisecondes() {
        return (int) (updatedTime % 1000);
    }

    // Setters
    public void setUpdatedTime (long updatedTime) {
        this.updatedTime = updatedTime ;
    }

    public void setEnPause (boolean enPause) {
        this.enPause = enPause ;
    }

    // Lancer le compteur
    public void start (long duree) {
        if (timer == null) {

            // Initialisation des données du compteur
            updatedTime = duree ;
            enPause = false ;
            secondesEcoulees = getSecondes() ;

            // Jeu du son associé à l'activité sportive
            jouerSon (0) ;

            // Affichage du compteur
            affichage() ;

            // Création du CountDownTimer
            timer = new CountDownTimer (updatedTime, COUNT_DOWN_INTERVAL) {

                // Callback fired on regular interval
                public void onTick (long millisUntilFinished) {
                    updatedTime = millisUntilFinished ;

                    // Ici, traitement à effectuer pour déclencher les sons de reprise
                    if (updatedTime < TEMPS_REPRISE && secondesEcoulees != getSecondes()) {
                        jouerSon (1) ;
                        secondesEcoulees = getSecondes() ;
                    }

                    affichage() ;
                }

                // Callback fired when the time is up
                public void onFinish() {
                    updatedTime = 0 ;
                    tour++ ;
                    affichage() ;
                    stop() ;
                    declencheActivites() ;  // Déclenchement de l'activité (sportive) suivante
                }
            }.start() ;   // Start the countdown
        }
    }

    // Mettre en pause le compteur
    public void pause() {
        stop() ; // arrêt du timer
        affichage() ; // mise à jour de l'affichage
    }

    // Arrete l'objet CountDownTimer et l'efface
    public void stop() {
        enPause = true ;
        if (timer != null) {
            timer.cancel() ;
            timer = null ;
        }
    }
}

