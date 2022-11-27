package com.example.efhi.Modele.Donnees ;

public class Seance {

    // Attributs
    private int tpsPreparation ;
    private int nbSequences ;
    private int nbCycles ;
    private int tpsTravail ;
    private int tpsRepos ;
    private int tpsReposLong ;

    // Constructeur
    public Seance (int tpsPreparation, int nbSequences, int nbCycles, int tpsTravail, int tpsRepos, int tpsReposLong) {
        this.tpsPreparation = tpsPreparation ;
        this.nbSequences = nbSequences ;
        this.nbCycles = nbCycles ;
        this.tpsTravail = tpsTravail ;
        this.tpsRepos = tpsRepos ;
        this.tpsReposLong = tpsReposLong ;
    }

    // Getters
    public int getTpsPreparation() {
        return tpsPreparation ;
    }

    public int getNbSequences() {
        return nbSequences ;
    }

    public int getNbCycles() {
        return nbCycles ;
    }

    public int getTpsTravail() {
        return tpsTravail ;
    }

    public int getTpsRepos() {
        return tpsRepos ;
    }

    public int getTpsReposLong() {
        return tpsReposLong ;
    }

    // Setters
    public void setTpsPreparation (int tpsPreparation) {
        this.tpsPreparation = tpsPreparation ;
    }

    public void setNbSequences (int nbSequences) {
        this.nbSequences = nbSequences ;
    }

    public void setNbCycles (int nbCycles) {
        this.nbCycles = nbCycles ;
    }

    public void setTpsTravail (int tpsTravail) {
        this.tpsTravail = tpsTravail ;
    }

    public void setTpsRepos (int tpsRepos) {
        this.tpsRepos = tpsRepos ;
    }

    public void setTpsReposLong (int tpsReposLong) {
        this.tpsReposLong = tpsReposLong ;
    }
}
