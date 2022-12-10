package com.example.efhi.Activites;

import static java.lang.Integer.parseInt;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChoixSeanceDefinitionActivity extends AppCompatActivity {

    public static final String SEANCE_SAISIE = "seance" ;
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static { System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ; LOGGER.setLevel(Level.INFO) ; }

    // Attributs
        // Vues
    private EditText vueEditPreparation ;
    private EditText vueEditSequences ;
    private EditText vueEditCycles ;
    private EditText vueEditTravail ;
    private EditText vueEditRepos ;
    private EditText vueEditReposLong ;
    private EditText vueEditTitre ;
    private ArrayList<EditText> listeEditText ;
        // Données
    private DatabaseClient cbdd ;
    private Seance seance ;
    private Seance seanceBDD ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_definition) ;

        // Récupération de l'instance de Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Récupération des EditText, et insertion dans la liste qui les regroupe (sauf le dernier)
        listeEditText = new ArrayList<>() ;
        vueEditPreparation = findViewById (R.id.activity_choix_seance_definition_tpsPreparation) ; listeEditText.add(vueEditPreparation) ;
        vueEditSequences = findViewById (R.id.activity_choix_seance_definition_nbSequences) ; listeEditText.add(vueEditSequences) ;
        vueEditCycles = findViewById (R.id.activity_choix_seance_definition_nbCycles) ; listeEditText.add(vueEditCycles) ;
        vueEditTravail = findViewById (R.id.activity_choix_seance_definition_tpsTravail) ; listeEditText.add(vueEditTravail) ;
        vueEditRepos = findViewById (R.id.activity_choix_seance_definition_tpsRepos) ; listeEditText.add(vueEditRepos) ;
        vueEditReposLong = findViewById (R.id.activity_choix_seance_definition_tpsReposLong) ; listeEditText.add(vueEditReposLong) ;
        vueEditTitre = findViewById(R.id.activity_choix_seance_definition_titre) ;

        if (savedInstanceState != null) {
            // Récupération de la séance enregistrée
            Seance seanceTemp = savedInstanceState.getParcelable(SEANCE_SAISIE) ;
            // Affichage des valeurs non null dans les EditTexts correspondants
            setTextAdapte (vueEditPreparation, seanceTemp.getTpsPreparation()) ;
            setTextAdapte (vueEditSequences, seanceTemp.getNbSequences()) ;
            setTextAdapte (vueEditCycles, seanceTemp.getNbCycles()) ;
            setTextAdapte (vueEditTravail, seanceTemp.getTpsTravail()) ;
            setTextAdapte (vueEditRepos, seanceTemp.getTpsRepos()) ;
            setTextAdapte (vueEditReposLong, seanceTemp.getTpsReposLong()) ;
        }
    }

    // Méthode utilitaire écrite pour le remplissage des EditTexts lors de l'éventuelle recréation de l'activité
    // Utilise la convention adoptée consistant à mettre à -1 un attribut entier de séance lors de la récupération des données si l'EditText correspondant est vide
    private void setTextAdapte (EditText vueEdit, int attribut) {
        vueEdit.setText(attribut != -1 ? Integer.toString(attribut) : "") ;
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {

        // Récupération des données saisies et sauvegarde dans l'objet seanceTemp créé
        Seance seanceTemp = recupererDonnees ("", false) ;

        // Sauvegarde des attributs
        savedInstanceState.putParcelable(SEANCE_SAISIE, seanceTemp) ;

        // "Always call the superclass so it can save the view hierarchy state"
        super.onSaveInstanceState(savedInstanceState) ;
    }


    public void clicBoutonValider (View view) {

        // Récupération des données
        seance = recupererDonnees ("avant de lancer les exercices", true) ;

        if (seance != null) {

            // Stockage de la séance dans l'application
            ((MonApplication) ChoixSeanceDefinitionActivity.this.getApplication()).setSeance(seance) ;

            // Lancement de l'activité suivante
            Intent intention = new Intent (ChoixSeanceDefinitionActivity.this, SeanceIntroductionActivity.class) ;
            startActivity (intention) ;
        }
    }

    public void clicBoutonEnregistrer (View view) {

        // Enregistrement d'un preset : vérifier que les paramètres chargés sont tous différents de ceux d'un preset existant
        // Afficher un toast dans les deux cas "enregistrement de preset" et "preset déjà existant"

        // Récupération des données
        seanceBDD = recupererDonnees ("avant d'enregistrer la séance", true) ;

        // Classe asynchrone permettant d'écrire dans la BDD la dernière séance (enregistrement de catégorie 'last') et de poursuivre l'application
        class SetLast extends AsyncTask<Void, Void, Seance> {

            @Override
            protected Seance doInBackground (Void... voids) {
                // On enregistre la séance dans la BDD
                cbdd.getAppDatabase().seanceDao().insert(seanceBDD) ;
                return seanceBDD ;
            }

            @Override
            protected void onPostExecute (Seance seanceBDD) {
                super.onPostExecute(seanceBDD) ;
                // On porte un toast lorsque la séance a été enregistrée dans la base
                Toast.makeText(getApplicationContext(), "La séance a été enregistrée", Toast.LENGTH_SHORT).show() ;
            }
        }

        if (seanceBDD != null) {
            // Création d'un objet de type SetLast et exécution de la demande asynchrone
            SetLast sl = new SetLast() ;
            sl.execute() ;
        }
    }


    public Seance recupererDonnees (String messageDonneesNonDisponibles, boolean validOuEnreg) {

        // 1. Vérification du remplissage des EditTexts (effectué seulement si on clique sur le bouton valider ou enregistrer)
        if (validOuEnreg && !areEditTextsFilled()) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs " + messageDonneesNonDisponibles, Toast.LENGTH_SHORT).show() ;
            return null ;
        }

        // 2. Récupération du contenu des différents EditText
        // Pas de vérification des données à effectuer, les editText sont de type "number", il faut juste convertir leur contenu en entier
        int tpsPreparation = parseIntAdapte (vueEditPreparation) ;
        int nbSequences = parseIntAdapte (vueEditSequences) ;
        int nbCycles = parseIntAdapte (vueEditCycles) ;
        int tpsTravail = parseIntAdapte (vueEditTravail) ;
        int tpsRepos = parseIntAdapte (vueEditRepos) ;
        int tpsReposLong = parseIntAdapte (vueEditReposLong) ;
        String chaineTitre = vueEditTitre.getText().toString() ;

        // 3. Creation et renvoi d'un objet de type Seance
        return new Seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong, "utilisateur", chaineTitre) ;
    }

    public boolean areEditTextsFilled() {
        // Vérification du remplissage des données (ne prend pas en compte le dernier EditText, non mis dans la liste)
        int i = 0 ;
        while (i < listeEditText.size() && !listeEditText.get(i).getText().toString().isEmpty()) { // tant que l'EditText n°i n'est pas vide, on continue
            i++ ;
        }
        return i == listeEditText.size() ;
    }

    // Méthode utilitaire écrite pour la récupération des données
    // Consiste à fournir une valeur de -1 à un attribut de séance si l'EditText correspondant est vide
    private int parseIntAdapte (EditText vueEdit) {
        String chaineVueEdit = vueEdit.getText().toString() ;
        return (!chaineVueEdit.isEmpty() ? parseInt(chaineVueEdit) : -1) ;
    }
}