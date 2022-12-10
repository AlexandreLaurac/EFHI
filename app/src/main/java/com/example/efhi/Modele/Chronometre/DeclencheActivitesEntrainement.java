package com.example.efhi.Modele.Chronometre;

import com.example.efhi.Activites.Declencheur;

import java.util.ArrayList;

/**
 * Classe proposant un mécanisme d'abonnement auditeur/source
 * En association avec l'interface Declencheur
 *
 */
public class DeclencheActivitesEntrainement {

    // Attributs
    protected int tour ;
    private Declencheur declencheur ;
    private ArrayList<Integer> durees ;

    // Constructeurs
    DeclencheActivitesEntrainement (Declencheur declencheur) {
        tour = 0 ;
        this.declencheur = declencheur ;
        durees = new ArrayList<>() ;
    }

    // Getter
    public int getTour() {
        return tour ;
    }

    // Setter
    public void setTour (int tour) {
        this.tour = tour ;
    }

    // Ajout des durées d'entrainement à la liste des durées
    public void addDuree (int duree) {
        durees.add(duree) ;
    }

    // Méthode activée par la source pour déclencher ses activités
    public void declencheActivites() {
        if (tour < durees.size()) {  // tour est mis à jour à la fin d'un décompte du compteur, et le compteur est déclenché par l'appel declencheur.declenche(durees.get(tour))
            declencheur.affichageSeance(tour) ;
            declencheur.declenche(durees.get(tour)) ;
        }
        else {
            declencheur.termine() ;
        }
    }

    public void affichage() {
        declencheur.miseAJourCompteur() ;
    }


}
