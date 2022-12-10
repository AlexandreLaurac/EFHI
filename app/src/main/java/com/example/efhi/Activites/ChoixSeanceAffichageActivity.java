package com.example.efhi.Activites;

import static java.lang.Integer.parseInt;

import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChoixSeanceAffichageActivity extends AppCompatActivity {

    // Attributs
    private boolean modification ; // état de l'interface
    private Seance seance ;        // Seance choisie
    private Seance seanceBis ;     // Seance éventuellement modifiée
    private Button boutonDuBas ;   // bouton du bas
        // TextViews + liste associée
    private TextView vueTexteTitre ;
    private TextView vueTextePreparation ;
    private TextView vueTexteSequences ;
    private TextView vueTexteCycles ;
    private TextView vueTexteTravail ;
    private TextView vueTexteRepos ;
    private TextView vueTexteReposLong ;
    private List<TextView> listeTextViews ;
        // EditTexts +  liste associée
    private EditText vueEditTitre ;
    private EditText vueEditPreparation ;
    private EditText vueEditSequences ;
    private EditText vueEditCycles ;
    private EditText vueEditTravail ;
    private EditText vueEditRepos ;
    private EditText vueEditReposLong ;
    private List<EditText> listeEditTexts ;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_affichage) ;

        // Etat de l'interface
        modification = false ;

        // Récupération (des paramètres) de la séance choisie
        seance = ((MonApplication) this.getApplication()).getSeance() ;

        // Récupération du bouton du bas
        boutonDuBas = findViewById (R.id.activity_choix_seance_affichage_boutonDuBas) ;

        // Récupération des vues
        recuperationDesVues() ;

        // Affichage initial
        affichageInitial() ;
    }


    public void recuperationDesVues() {

        // Récupération des TextView
        listeTextViews = new ArrayList<>() ;
        vueTextePreparation = findViewById (R.id.activity_choix_seance_affichage_texteTpsPreparation) ; listeTextViews.add(vueTextePreparation) ;
        vueTexteSequences = findViewById (R.id.activity_choix_seance_affichage_texteNbSequences) ; listeTextViews.add(vueTexteSequences) ;
        vueTexteCycles = findViewById (R.id.activity_choix_seance_affichage_texteNbCycles) ; listeTextViews.add(vueTexteCycles) ;
        vueTexteTravail = findViewById (R.id.activity_choix_seance_affichage_texteTpsTravail) ; listeTextViews.add(vueTexteTravail) ;
        vueTexteRepos = findViewById (R.id.activity_choix_seance_affichage_texteTpsRepos) ; listeTextViews.add(vueTexteRepos) ;
        vueTexteReposLong = findViewById (R.id.activity_choix_seance_affichage_texteTpsReposLong) ; listeTextViews.add(vueTexteReposLong) ;
        vueTexteTitre = findViewById (R.id.activity_choix_seance_affichage_texteTitre) ; //listeTextViews.add(vueTexteTitre) ;


        // Récupération des EditText
        listeEditTexts = new ArrayList<>() ;
        vueEditPreparation = findViewById (R.id.activity_choix_seance_affichage_editTpsPreparation) ; listeEditTexts.add(vueEditPreparation) ;
        vueEditSequences = findViewById (R.id.activity_choix_seance_affichage_editNbSequences) ; listeEditTexts.add(vueEditSequences) ;
        vueEditCycles = findViewById (R.id.activity_choix_seance_affichage_editNbCycles) ; listeEditTexts.add(vueEditCycles) ;
        vueEditTravail = findViewById (R.id.activity_choix_seance_affichage_editTpsTravail) ; listeEditTexts.add(vueEditTravail) ;
        vueEditRepos = findViewById (R.id.activity_choix_seance_affichage_editTpsRepos) ; listeEditTexts.add(vueEditRepos) ;
        vueEditReposLong = findViewById (R.id.activity_choix_seance_affichage_editTpsReposLong) ; listeEditTexts.add(vueEditReposLong) ;
        //vueEditTitre = findViewById (R.id.activity_choix_seance_affichage_editTitre) ; listeEditTexts.add(vueEditTitre) ;

    }

    public void affichageInitial() {

        // Mise à jour du texte des vues Texte en fonction des valeurs de 'seance'
        String titre = seance.getTitre().isEmpty() ? "Seance" : seance.getTitre() ;
        vueTexteTitre.setText(titre) ;

        String textePreparation = "" + seance.getTpsPreparation() ;
        vueTextePreparation.setText(textePreparation) ;

        String texteSequences = "" + seance.getNbSequences() ;
        vueTexteSequences.setText(texteSequences) ;

        String texteCycles = "" + seance.getNbCycles() ;
        vueTexteCycles.setText(texteCycles) ;

        String texteTravail = "" + seance.getTpsTravail() ;
        vueTexteTravail.setText(texteTravail) ;

        String texteRepos = "" + seance.getTpsRepos() ;
        vueTexteRepos.setText(texteRepos) ;

        String texteReposLong = "" + seance.getTpsReposLong() ;
        vueTexteReposLong.setText(texteReposLong) ;
    }

    public void affichageModification() {

        // Masquage des TextViews
        for (TextView vue : listeTextViews) {
            //vue.setVisibility(View.GONE) ;
            vue.setText("");
        }

        // Affichage des EditTexts
        for (EditText vue : listeEditTexts) {
            vue.setVisibility(View.VISIBLE) ;

        }

        // Remplissage des EditTexts avec les valeurs de la séance choisie
        /*
        String titre ;
        switch (seance.getTitre()) {
            case "" :
            case "Dernière séance" :
                titre = null ;
                break ;
            default :
                titre = seance.getTitre() ;
        }
        vueEditTitre.setText(titre) ;
        //vueEditTitre.setAutofillHints("Titre (facultatif)");
         */
        vueEditPreparation.setText(Integer.toString(seance.getTpsPreparation())) ;
        vueEditSequences.setText(Integer.toString(seance.getNbSequences())) ;
        vueEditCycles.setText(Integer.toString(seance.getNbCycles())) ;
        vueEditTravail.setText(Integer.toString(seance.getTpsTravail())) ;
        vueEditRepos.setText(Integer.toString(seance.getTpsRepos())) ;
        vueEditReposLong.setText(Integer.toString(seance.getTpsReposLong())) ;
    }

    public void clicBoutonDuBas (View view) {
        // L'interface est dans l'état affichage (pas modification)
        if (!modification) {
            modification = true ;
            //String titreBoutonDuBas = "Enregistrer" ;
            //boutonDuBas.setText(titreBoutonDuBas) ;
            boutonDuBas.setVisibility(View.GONE) ;
            affichageModification() ;
        }
        // L'interface est dans l'état modification
        else {
            //enregistrerSeance() ;
        }
    }

    public void enregistrerSeance() {
        // Ici, même mécanisme que pour l'enregistrement d'une séance dans l'activité de définition
        // à ceci près qu'il faut ici regarder si on a modifié les paramètres
    }

    public void clicBoutonValider (View view) {

        if (modification) {
            // Récupération des données
            seanceBis = recupererDonnees ("avant de lancer les exercices") ;
        }
        else {
            seanceBis = seance ;
        }

        if (seanceBis != null) {

            // Stockage de la séance dans l'application
            ((MonApplication) ChoixSeanceAffichageActivity.this.getApplication()).setSeance(seanceBis);

            // Lancement de l'activité suivante
            Intent intention = new Intent(ChoixSeanceAffichageActivity.this, SeanceIntroductionActivity.class) ;
            startActivity(intention) ;
        }
    }

    public Seance recupererDonnees (String message) {

        // 1. Vérification du remplissage des EditTexts
        if (!areEditTextsFilled()) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs " + message, Toast.LENGTH_SHORT).show() ;
            return null ;
        }

        // 2. Récupération du contenu des différents EditText
        // Pas de vérification des données à effectuer, les editText sont de type "number", il faut juste convertir leur contenu en entier
        int tpsPreparation = parseInt (vueEditPreparation.getText().toString()) ;
        int nbSequences = parseInt (vueEditSequences.getText().toString()) ;
        int nbCycles = parseInt (vueEditCycles.getText().toString()) ;
        int tpsTravail = parseInt (vueEditTravail.getText().toString()) ;
        int tpsRepos = parseInt (vueEditRepos.getText().toString()) ;
        int tpsReposLong = parseInt (vueEditReposLong.getText().toString()) ;
        //String chaineTitre = vueEditTitre.getText().toString();

        // 3. Creation et renvoi d'un objet de type Seance
        return new Seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong, "utilisateur", seance.getTitre()) ;
    }

    public boolean areEditTextsFilled() {
        // Vérification du remplissage des données (ne prend pas en compte le dernier EditText, non mis dans la liste)
        int i = 0 ;
        while (i < listeEditTexts.size() && !listeEditTexts.get(i).getText().toString().isEmpty()) { // tant que l'EditText n°i n'est pas vide, on continue
            i++ ;
        }
        return i == listeEditTexts.size() ;
    }


}