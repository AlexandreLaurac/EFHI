package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private boolean existSeancesEnregistrees ;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_menu) ;

        // Existence de séances enregistrées ou non
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
                    findViewById(R.id.activity_choix_seance_menu_bouton_seances_enregistrees).setVisibility(View.VISIBLE) ;
                }

                // Post traitements concernant la dernière séance
                if (lasts.size() == 0) { // Il n'y a pas d'enregistrement dont la catégorie est 'last' dans la BDD
                    ((MonApplication) ChoixSeanceMenuActivity.this.getApplication()).setLastExists(false) ;
                }
                else {
                    ((MonApplication) ChoixSeanceMenuActivity.this.getApplication()).setLastExists(true) ;
                    findViewById(R.id.activity_choix_seance_menu_bouton_derniere_seance).setVisibility(View.VISIBLE) ;
                    derniereSeance = lasts.get(0) ;
                }
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
        lanceSeanceEnBaseActivity (categorie) ;
    }

    public void clicBoutonSeancesEnregistrees (View view) {
        String categorie = "utilisateur" ;
        lanceSeanceEnBaseActivity (categorie) ;
    }

    public void lanceSeanceEnBaseActivity (String categorie) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceEnBaseActivity.class) ;
        intention.putExtra(ChoixSeanceEnBaseActivity.CATEGORIE_SEANCES, categorie) ;
        startActivity (intention) ;
    }

    public void clicBoutonDerniereSeance (View view) {
        ((MonApplication) this.getApplication()).setSeance(derniereSeance) ;
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceAffichageActivity.class) ;
        startActivity (intention) ;
    }
}