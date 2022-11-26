package com.example.efhi.Modele.Donnees;

public class Cycle {

    // Attributs
    private int tpsTravail ;
    private int tpsRepos ;

    // Constructeur
    public Cycle (int tpsTravail, int tpsRepos) {
        this.tpsTravail = tpsTravail ;
        this.tpsRepos = tpsRepos ;
    }

    // Getters
    public int getTpsTravail() {
        return tpsTravail ;
    }

    public int getTpsRepos() {
        return tpsRepos ;
    }

    //Setters
    public void setTpsTravail (int tpsTravail) {
        this.tpsTravail = tpsTravail ;
    }

    public void setTpsRepos (int tpsRepos) {
        this.tpsRepos = tpsRepos ;
    }
}
