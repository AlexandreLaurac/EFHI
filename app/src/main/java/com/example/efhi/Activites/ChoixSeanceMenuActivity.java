package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChoixSeanceMenuActivity extends AppCompatActivity {

    // Attribut statique (LOGGER)
    private static Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
    private DatabaseClient cbdd ;
    private Seance derniereSeance ;
    private boolean existDerniereSeance ;
    private boolean existSeancesEnregistrees ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_menu) ;

        // Existence de séances enregistrées ou non
        existDerniereSeance = false ;
        existSeancesEnregistrees = false ;

        // Récupération de l'instance du Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Vérification de l'existence d'un enregistrement de la précédente séance
        verifExistenceSeances() ;
    }


    private void verifExistenceSeances() {

        // Classe asynchrone permettant de récupérer les séances enregistrées par l'utilisateur et la dernière séance, si elles existent, et d'afficher le bouton des activités associées
        class GetLast extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground (Void... voids) {

                // Séances enregistrées par l'utilisateur
                List<Seance> seancesEnregistrees = cbdd.getAppDatabase().seanceDao().getCategorie("utilisateur") ;
                if (seancesEnregistrees.size() > 0) {
                    existSeancesEnregistrees = true ;
                }

                // Dernière séance
                List<Seance> lasts = cbdd.getAppDatabase().seanceDao().getCategorie("last") ;
                return lasts ;
            }

            @Override
            protected void onPostExecute (List<Seance> lasts) {
                super.onPostExecute(lasts) ;

                // Post traitements concernant la dernière séance
                if (existSeancesEnregistrees) {
                    findViewById(R.id.activity_choix_seance_menu_bouton_seances_enregistrees).setBackgroundColor(Color.GRAY) ;
                }

                // Post traitements concernant la dernière séance
                /*if (lasts.size() == 0) { // Il n'y a pas d'enregistrement dont la catégorie est 'last' dans la BDD
                }
                else {*/
                if (lasts.size() > 0) {
                    existDerniereSeance = true ;
                    findViewById(R.id.activity_choix_seance_menu_bouton_derniere_seance).setBackgroundColor(Color.GRAY) ;
                    derniereSeance = lasts.get(0) ;
                }
                ((MonApplication) ChoixSeanceMenuActivity.this.getApplication()).setLastExists(existDerniereSeance) ;
            }
        }

        // Création d'un objet de type GetLast et execution de la demande asynchrone
        GetLast gl = new GetLast() ;
        gl.execute() ;
    }


    public void clicBoutonDefinirSeance (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceDefinitionActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonPresets (View view) {
        String categorie = "preset" ;
        lanceChoixSeanceEnBaseActivity (categorie) ;
    }

    public void clicBoutonSeancesEnregistrees (View view) {
        if (!existSeancesEnregistrees) {
            Intent intention = new Intent (this, ChoixSeanceNonDisponibleActivity.class) ;
            String titre = "Il n'y a aucune séance enregistrée dans l'application" ;
            String description = "Pour en enregistrer une, allez dans \"Définition de séance\" et cliquez sur \"ENREGISTRER\" après avoir défini une séance" ;
            intention.putExtra(ChoixSeanceNonDisponibleActivity.TITRE, titre) ;
            intention.putExtra(ChoixSeanceNonDisponibleActivity.EXPLICATION, description) ;
            startActivity (intention) ;
        }
        else {
            String categorie = "utilisateur" ;
            lanceChoixSeanceEnBaseActivity (categorie) ;
        }
    }

    public void lanceChoixSeanceEnBaseActivity (String categorie) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceEnBaseActivity.class) ;
        intention.putExtra(ChoixSeanceEnBaseActivity.CATEGORIE_SEANCES, categorie) ;
        startActivity (intention) ;
    }

    public void clicBoutonDerniereSeance (View view) {
        if (!existDerniereSeance) {
            Intent intention = new Intent (this, ChoixSeanceNonDisponibleActivity.class) ;
            String titre = "Il n'y a pas de séance précédente" ;
            String description = "Pour cela, il faut réaliser au moins une séance" ;
            intention.putExtra(ChoixSeanceNonDisponibleActivity.TITRE, titre) ;
            intention.putExtra(ChoixSeanceNonDisponibleActivity.EXPLICATION, description) ;
            startActivity (intention) ;
        }
        else {
            ((MonApplication) this.getApplication()).setSeance(derniereSeance) ;
            Intent intention = new Intent(ChoixSeanceMenuActivity.this, ChoixSeanceAffichageActivity.class) ;
            startActivity(intention) ;
        }
    }
}