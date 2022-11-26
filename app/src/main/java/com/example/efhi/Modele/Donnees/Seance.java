package com.example.efhi.Modele.Donnees;

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

    // Setters
    public void setTpsPreparation (int tpsPreparation) {
        this.tpsPreparation = tpsPreparation ;
    }

    public void setNbSequences (int nbSequences) {
        this.nbSequences = nbSequences ;
    }

    public void setSequence (int nbCycles, int tpsReposLong, int tpsTravail, int tpsRepos) {
        this.sequence.setNbCycles(nbCycles) ;
        this.sequence.setTpsReposLong(tpsReposLong) ;
        this.sequence.setCycle(tpsTravail, tpsRepos) ;
    }
}
