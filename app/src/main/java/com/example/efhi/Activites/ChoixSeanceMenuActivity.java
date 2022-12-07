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
    private Button boutonDerniereSeance ;
    private Seance derniereSeance ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_menu) ;

        // Récupération du bouton "dernière séance"
        boutonDerniereSeance = findViewById (R.id.activity_choix_seance_menu_bouton_derniere_seance) ;

        // Récupération de l'instance du Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Vérification de l'existence d'un enregistrement de la précédente séance
        verifLastExists() ;
    }


    private void verifLastExists() {

        // Classe asynchrone permettant de récupérer la dernière séance, si elle existe, et d'afficher le bouton de l'activité associée
        class GetLast extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground (Void... voids) {
                List<Seance> lasts = cbdd.getAppDatabase().seanceDao().getCategorie("last") ;

                //long nbOfElements = cbdd.getAppDatabase().seanceDao().getNbOfElements() ;
                //LOGGER.log(Level.INFO, "ChoixSeanceMenuActivity, nombre d'éléments : " + nbOfElements) ;

                return lasts ;
            }

            @Override
            protected void onPostExecute(List<Seance> lasts) {
                super.onPostExecute(lasts) ;

                if (lasts.size() == 0) { // Il n'y a pas d'enregistrement dont la catégorie est 'last' dans la BDD
                    ((MonApplication) ChoixSeanceMenuActivity.this.getApplication()).setLastExists(false) ;
                    boutonDerniereSeance.setVisibility(View.INVISIBLE) ;  // On cache le bouton permettant d'accéder à l'activité affichant les paramètres de la dernière séance
                }
                else {
                    ((MonApplication) ChoixSeanceMenuActivity.this.getApplication()).setLastExists(true) ;
                    boutonDerniereSeance.setVisibility(View.VISIBLE) ;
                    derniereSeance = lasts.get(0) ;
                    // LOGGER.log(Level.INFO, "Id de la séance récupérée : " + derniereSeance.getId()) ;
                }
            }
        }

        // Création d'un objet de type GetLast et execution de la demande asynchrone
        GetLast gl = new GetLast() ;
        gl.execute() ;
    }


    public void clicBoutonSeancesEnregistrees (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceEnregistreesActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonPresets (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeancePresetsActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonDefinirSeance (View view) {
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceDefinitionActivity.class) ;
        startActivity (intention) ;
    }

    public void clicBoutonDerniereSeance (View view) {
        ((MonApplication) this.getApplication()).setSeance(derniereSeance) ;
        Intent intention = new Intent (ChoixSeanceMenuActivity.this, ChoixSeanceDerniereSeanceActivity.class) ;
        startActivity (intention) ;
    }
}