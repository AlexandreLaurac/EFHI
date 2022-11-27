package com.example.efhi.Activites;

import com.example.efhi.Modele.Chronometre.DeclencheActivitesEntrainement;

/**
 * Interface intervenant dans le mécanisme d'abonnement auditeur/source
 * En association avec la classe DeclencheActivitesEntrainement
 *
 */
public interface Declencheur {  // Au départ, une seule méthode dans cette interface : declenche
                                // Maintenant qu'il y en a d'autres qui font d'autres types de choses, changer le nom de l'interface

    public void declenche (int duree) ;

    public void termine() ;

    public void miseAJourCompteur() ;

    public void affichageSeance (int tour) ;

}

