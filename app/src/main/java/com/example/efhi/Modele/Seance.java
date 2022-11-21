package com.example.efhi.Modele ;

public class Seance {

    // Attributs
    private int tpsPreparation ;
    private int nbSequences ;
    private Sequence sequence ;

    // Constructeur
    public Seance (int tpsPreparation, int nbSequences, int nbCycles, int tpsReposLong, int tpsTravail, int tpsRepos) {
        this.tpsPreparation = tpsPreparation ;
        this.nbSequences = nbSequences ;
        this.sequence = new Sequence (nbCycles, tpsReposLong, tpsTravail, tpsRepos) ;
    }

    // Getters
    public int getTpsPreparation() {
        return tpsPreparation ;
    }

    public int getNbSequences() {
        return nbSequences ;
    }

    public Sequence getSequence() {
        return sequence ;
    }

}
