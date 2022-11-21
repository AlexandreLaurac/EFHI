package com.example.efhi.Modele;

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

}
