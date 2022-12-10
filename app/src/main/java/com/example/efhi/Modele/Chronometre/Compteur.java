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
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static { System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ; LOGGER.setLevel(Level.INFO) ; }

    // Attributs
    private CountDownTimer timer ;
    private long updatedTime ;
    private boolean enPause ;

    // Constructeur
    public Compteur (Declencheur declencheur, boolean enPause) {
        super (declencheur) ;
        updatedTime = 0 ;
        this.enPause = enPause ;
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
            updatedTime = duree ; // + COUNT_DOWN_INTERVAL ; ? // Pour voir le temps initial
            enPause = false ;
            affichage() ;                                      // pour voir le temps initial

            // Création du CountDownTimer
            timer = new CountDownTimer(updatedTime, 10) {

                // Callback fired on regular interval
                public void onTick (long millisUntilFinished) {
                    updatedTime = millisUntilFinished ;
                    affichage() ;
                }

                // Callback fired when the time is up
                public void onFinish() {
                    updatedTime = 0 ;
                    affichage() ;
                    stop() ;
                    tour++ ;
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
    private void stop() {
        enPause = true ;
        if (timer != null) {
            timer.cancel() ;
            timer = null ;
        }
    }
}

