package com.example.efhi.Modele.Chronometre;

import android.os.CountDownTimer;

import com.example.efhi.Activites.Declencheur;

/**
 * Created by fbm on 24/10/2017.
 */
public class Compteur extends DeclencheActivitesEntrainement {

    // Attribut de classe
    private static final int COUNT_DOWN_INTERVAL = 10 ;

    // Attributs
    private long updatedTime ;
    private CountDownTimer timer ;

    public Compteur (Declencheur declencheur) {
        super (declencheur) ;
        updatedTime = 0 ;
    }

    // Lancer le compteur
    public void start (long duree) {
        if (timer == null) {
            updatedTime = duree ; // + COUNT_DOWN_INTERVAL ; ? // Pour voir le temps initial
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
                    declencheActivites() ;  // Déclenchement de l'activité (sportive) suivante
                }
            }.start() ;   // Start the countdown
        }
    }

    // Mettre en pause le compteur
    public void pause() {
        if (timer != null) {

            // Arreter le timer
            stop() ;

            // Mise à jour
            affichage() ;
        }
    }

    // Arrete l'objet CountDownTimer et l'efface
    private void stop() {
        timer.cancel() ;
        timer = null ;
    }

    // Getters
    public long getUpdatedTime() {
        return updatedTime ;
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

}

