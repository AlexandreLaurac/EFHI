package com.example.efhi.Activites;

/**
 * Interface intervenant dans le mécanisme d'abonnement auditeur/source
 * En association avec la classe DeclencheActivitesEntrainement
 *
 */
public interface Declencheur {  // Au départ, une seule méthode dans cette interface : declenche
                                // Le nom serait à revoir avec les autres méthodes ajoutées

    public void declenche (int duree) ;

    public void termine() ;

    public void miseAJourCompteur() ;

    public void affichageSeance (int tour) ;

    public void jeuSon (int typeSon) ;

}

