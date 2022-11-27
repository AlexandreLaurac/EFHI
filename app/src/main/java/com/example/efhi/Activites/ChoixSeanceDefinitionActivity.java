package com.example.efhi.Activites;

import static java.lang.Integer.parseInt;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.efhi.Modele.Donnees.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.ArrayList;


public class ChoixSeanceDefinitionActivity extends AppCompatActivity {

    // Attributs
    private EditText preparation ;
    private EditText sequences ;
    private EditText cycles ;
    private EditText travail ;
    private EditText repos ;
    private EditText reposLong ;
    private ArrayList<EditText> listeEditText ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_definition) ;

        // Récupération des EditText, et insertion dans la liste qui les regroupe
        listeEditText = new ArrayList<>() ;
        preparation = findViewById (R.id.activity_choix_seance_definition_tpsPreparation) ; listeEditText.add(preparation) ;
        sequences = findViewById (R.id.activity_choix_seance_definition_nbSequences) ; listeEditText.add(sequences) ;
        cycles = findViewById (R.id.activity_choix_seance_definition_nbCycles) ; listeEditText.add(cycles) ;
        travail = findViewById (R.id.activity_choix_seance_definition_tpsTravail) ; listeEditText.add(travail) ;
        repos = findViewById (R.id.activity_choix_seance_definition_tpsRepos) ; listeEditText.add(repos) ;
        reposLong = findViewById (R.id.activity_choix_seance_definition_tpsReposLong) ; listeEditText.add(reposLong) ;
    }

    public void clicBoutonValider (View view) {

        // Vérification du remplissage des données
        int i = 0 ;
        while (i < listeEditText.size() && !listeEditText.get(i).getText().toString().isEmpty()) { // tant que l'EditText n°i n'est pas vide, on continue
            i++ ;
        }
        if (i < listeEditText.size()) {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs avant de lancer les exercices", Toast.LENGTH_SHORT).show() ;
        }
        // Récupération et stockage des données dans un objet seance
        else {
            // Récupération du contenu des différents EditText  -- A FAIRE : VERIFICATION DES DONNEES !!! OU ALORS AFFICHER UN CLAVIER QUI NE PROPOSE QUE DES NOMBRES
            int tpsPreparation = parseInt (preparation.getText().toString()) ;
            int nbSequences = parseInt (sequences.getText().toString()) ;
            int nbCycles = parseInt (cycles.getText().toString()) ;
            int tpsTravail = parseInt (travail.getText().toString()) ;
            int tpsRepos = parseInt (repos.getText().toString()) ;
            int tpsReposLong = parseInt (reposLong.getText().toString()) ;

            // Creation d'un objet de données de type Seance et ajout à la classe MonApplication
            Seance seance = new Seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong) ;
            ((MonApplication) ChoixSeanceDefinitionActivity.this.getApplication()).setSeance(seance) ;

            // Lancement de l'activité suivante
            Intent intention = new Intent (ChoixSeanceDefinitionActivity.this, SeanceIntroductionActivity.class) ;
            startActivity (intention) ;
        }
    }

    public void clicBoutonEnregistrer (View view) {
        // Enregistrement d'un preset : vérifier que les paramètres chargés sont tous différents de ceux d'un preset existant
        // Afficher un toast dans les deux cas "enregistrement de preset" et "preset déjà existant
    }
}