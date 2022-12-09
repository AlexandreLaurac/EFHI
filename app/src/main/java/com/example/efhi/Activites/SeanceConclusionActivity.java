package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SeanceConclusionActivity extends AppCompatActivity {

    // Attribut statique (LOGGER)
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
    private DatabaseClient cbdd ;
    //private boolean lastExists ;
    //private Seance seance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_seance_conclusion) ;

        // Récupération de l'instance du Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Récupération des données de l'application
        //boolean lastExists = ((MonApplication) this.getApplication()).getLastExists() ;
        //Seance seance = ((MonApplication) this.getApplication()).getSeance() ;
    }


    public void writesLastAndContinue (int code) {

        // Classe asynchrone permettant d'écrire dans la BDD la dernière séance (enregistrement de catégorie 'last') et de poursuivre l'application
        class SetLast extends AsyncTask<Void, Void, Seance> {

            @Override
            protected Seance doInBackground (Void... voids) {

                // Récupération des données de MonApplication (existence d'une séance précédemment enregistrée et séance courante)
                boolean lastExists = ((MonApplication) SeanceConclusionActivity.this.getApplication()).getLastExists() ;
                Seance seance = ((MonApplication) SeanceConclusionActivity.this.getApplication()).getSeance() ;

                // Modification du titre et de la catégorie
                seance.setTitre("Dernière séance") ;
                seance.setCategorie("last") ;

                // Ecriture dans la base (update ou insert selon que 'last' existe déjà ou non)
                if (lastExists) {
                    // Récupération de la dernière séance
                    List<Seance> lasts = cbdd.getAppDatabase().seanceDao().getCategorie("last") ;
                    Seance last = lasts.get(0) ;
                    // Modification de l'id
                    seance.setId(last.getId()) ;
                    // Mise à jour de l'enregistrement avec la séance courante
                    cbdd.getAppDatabase().seanceDao().update(seance) ;
                }
                else {
                    // Attribution d'un id
                    long maxId = cbdd.getAppDatabase().seanceDao().getMaxId() ;
                    seance.setId(maxId+1) ;  // Au cas où une dernière séance ne serait pas en base et qu'on aurait choisi une séance existante,
                                             // l'instruction suivante cherchera à insérer en base un enregistrement d'id existant, ce qui provoquera
                                             // une violation de contrainte d'unicité. Pour l'éviter, on fixe donc l'id à la valeur la plus élevée + 1
                                             // (mais ce n'est vraiment valable que pour les premières utilisations de l'application)
                    // Insertion dans la base
                    cbdd.getAppDatabase().seanceDao().insert(seance) ;
                }

                return seance ;
            }

            @Override
            protected void onPostExecute (Seance seance) {
                super.onPostExecute(seance) ;

                // Poursuite de l'application (on recommence les activités sportives ou on quitte l'application)
                if (code == 1) {
                    recommencer() ;
                }
                else if (code == 0) {
                    quitter() ;
                }
            }
        }

        // Création d'un objet de type GetLast et execution de la demande asynchrone
        SetLast sl = new SetLast() ;
        sl.execute() ;
    }


    public void clicBoutonRecommencer (View view) {
        writesLastAndContinue (1) ;
    }

    public void clicBoutonQuitter (View view) {
        writesLastAndContinue (0) ;
    }

    public void recommencer() {
        // Retour à l'activité de menu de choix de séance
        Intent intention = new Intent (this, ChoixSeanceMenuActivity.class) ;
        intention.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity (intention) ;
    }

    public void quitter() {
        // Quitter l'application - ou alors emmener vers une dernière vue qui dit au revoir à l'utilisateur et s'éteint toute seule ?
        finishAffinity() ;
        System.exit(0) ;
    }
}
