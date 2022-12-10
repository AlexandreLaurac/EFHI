package com.example.efhi.Modele.Donnees ;

import com.example.efhi.Modele.BDD.Seance;


public class EtatSeance {

    // Attributs
    private final Seance seance ;
    private Activite activite ;
    private int numSeq ;
    private int numCycle ;
    private int posCycle ;

    // Constructeur
    public EtatSeance (Seance seance) {  // Initialisation de l'objet en un état de la séance qui correspond à la phase de préparation
        this.seance = seance ;
        activite = Activite.PREPARATION ;
        numSeq = 0 ;
        numCycle = 0 ;
        posCycle = 0 ;
    }

    // Getters
    public int getNumSeq() {
        return numSeq ;
    }

    public int getNumCycle() {
        return numCycle ;
    }

    // Setters
    public void setEtatSeance (int tour) {
        if (tour >= 1) {

            // Calcul des numéros de cycle et de séquence, et de la position dans le cycle, à partir du tour (indice de la liste des activités physiques)
            int nbCycles = seance.getNbCycles() ;
            numSeq = (tour-1) / (2 * nbCycles) + 1 ;
            numCycle = ((tour-1) - 2 * nbCycles * (numSeq - 1)) / 2 + 1 ;
            posCycle = (tour-1) % 2 + 1 ;

            // Activité
            if (posCycle == 1) {
                activite = Activite.TRAVAIL ;
            }
            else if (numCycle < seance.getNbCycles()) {  // posCycle == 2
                activite = Activite.REPOS ;
            }
            else if (numCycle == seance.getNbCycles()) {
                activite = Activite.REPOS_LONG ;
            }
        }
    }

    // Méthodes d'état
    public boolean estEnPreparation() {
        return activite == Activite.PREPARATION ;
    }

    public boolean estEnSequence() {
        return activite != Activite.PREPARATION ;
    }

    public boolean estEnTravail() {
        return activite == Activite.TRAVAIL ;
    }

    public boolean estEnRepos() {
        return activite == Activite.REPOS ;
    }

    public boolean estEnReposLong() {
        return activite == Activite.REPOS_LONG ;
    }
}
