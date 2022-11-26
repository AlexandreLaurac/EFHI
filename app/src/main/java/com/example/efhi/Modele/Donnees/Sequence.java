package com.example.efhi.Modele.Donnees;

public class Sequence {

    // Attributs
    private int nbCycles ;
    private int tpsReposLong ;
    private Cycle cycle ;

    // Constructeur
    public Sequence (int nbCycles, int tpsReposLong, int tpsTravail, int tpsRepos) {
        this.nbCycles = nbCycles ;
        this.tpsReposLong = tpsReposLong ;
        this.cycle = new Cycle (tpsTravail, tpsRepos) ;
    }

    // Getters
    public int getNbCycles() {
        return nbCycles ;
    }

    public int getTpsReposLong() {
        return tpsReposLong ;
    }

    public Cycle getCycle() {
        return cycle ;
    }

    // Setters
    public void setNbCycles (int nbCycles) {
        this.nbCycles = nbCycles ;
    }

    public void setTpsReposLong (int tpsReposLong) {
        this.tpsReposLong = tpsReposLong ;
    }

    public void setCycle (int tpsTravail, int tpsRepos) {
        this.cycle.setTpsTravail(tpsTravail) ;
        this.cycle.setTpsRepos(tpsRepos) ;
    }
}
