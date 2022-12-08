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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChoixSeanceDefinitionActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
        // Vues
    private EditText preparation ;
    private EditText sequences ;
    private EditText cycles ;
    private EditText travail ;
    private EditText repos ;
    private EditText reposLong ;
    private EditText titre ;
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
        preparation = findViewById (R.id.activity_choix_seance_definition_tpsPreparation) ; listeEditText.add(preparation) ;
        sequences = findViewById (R.id.activity_choix_seance_definition_nbSequences) ; listeEditText.add(sequences) ;
        cycles = findViewById (R.id.activity_choix_seance_definition_nbCycles) ; listeEditText.add(cycles) ;
        travail = findViewById (R.id.activity_choix_seance_definition_tpsTravail) ; listeEditText.add(travail) ;
        repos = findViewById (R.id.activity_choix_seance_definition_tpsRepos) ; listeEditText.add(repos) ;
        reposLong = findViewById (R.id.activity_choix_seance_definition_tpsReposLong) ; listeEditText.add(reposLong) ;
        titre = findViewById(R.id.activity_choix_seance_definition_titre) ;
    }


    public void clicBoutonValider (View view) {

        // Récupération des données
        seance = recupererDonnees ("avant de lancer les exercices") ;

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
        seanceBDD = recupererDonnees ("avant d'enregistrer la séance") ;

        // Classe asynchrone permettant d'écrire dans la BDD la dernière séance (enregistrement de catégorie 'last') et de poursuivre l'application
        class SetLast extends AsyncTask<Void, Void, Seance> {

            @Override
            protected Seance doInBackground (Void... voids) {
                // On enregistre la séance dans la BDD
                cbdd.getAppDatabase().seanceDao().insert(seanceBDD) ;

                long nbOfElements = cbdd.getAppDatabase().seanceDao().getNbOfElements() ;
                LOGGER.log(Level.INFO, "ChoixSeanceDefinitionActivity - nombre d'éléments dans la base : " + nbOfElements) ;

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


    public Seance recupererDonnees (String message) {

        // 1. Vérification du remplissage des EditTexts
        if (!areEditTextsFilled()) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs " + message, Toast.LENGTH_SHORT).show() ;
            return null ;
        }

        // 2. Récupération du contenu des différents EditText  -- A FAIRE : VERIFICATION DES DONNEES !!! OU ALORS AFFICHER UN CLAVIER QUI NE PROPOSE QUE DES NOMBRES
        int tpsPreparation = parseInt (preparation.getText().toString()) ;
        int nbSequences = parseInt (sequences.getText().toString()) ;
        int nbCycles = parseInt (cycles.getText().toString()) ;
        int tpsTravail = parseInt (travail.getText().toString()) ;
        int tpsRepos = parseInt (repos.getText().toString()) ;
        int tpsReposLong = parseInt (reposLong.getText().toString()) ;
        String chaineTitre = titre.getText().toString();

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
}