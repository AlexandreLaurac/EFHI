package com.example.efhi.Modele.Chronometre;

import static java.sql.DriverManager.println;

import com.example.efhi.Activites.Declencheur;

import java.util.ArrayList;

/**
 * Classe proposant un mécanisme d'abonnement auditeur/source
 * En association avec l'interface Declencheur
 *
 */
public class DeclencheActivitesEntrainement {

    // Attributs
    private int tour ;
    private Declencheur declencheur ;
    private ArrayList<Integer> durees ;

    // Constructeurs
    DeclencheActivitesEntrainement (Declencheur declencheur) {
        tour = 0 ;
        this.declencheur = declencheur ;
        durees = new ArrayList<>() ;
    }

    // Ajout des durées d'entrainement à la liste des durées
    public void addDuree (int duree) {
        durees.add(duree) ;
    }

    // Méthode activée par la source pour déclencher ses activités
    public void declencheActivites() {
        if (tour < durees.size()) {
            declencheur.affichageSeance(tour) ;
            declencheur.declenche(durees.get(tour)) ;
            tour++ ;
        }
        else {
            declencheur.termine() ;
        }
    }

    public void affichage() {
        declencheur.miseAJourCompteur() ;
    }


}
