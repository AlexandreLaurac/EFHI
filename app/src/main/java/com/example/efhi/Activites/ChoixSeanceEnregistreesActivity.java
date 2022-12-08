package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChoixSeanceEnregistreesActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
    private DatabaseClient cbdd ;
    //private TextView vueTexte ;
        // Attributs liés à l'affichage des séances sous forme de liste
    private RecyclerView vue ;
    private AdaptateurDeSeance adaptateur ;
    private RecyclerView.LayoutManager gestDisp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_enregistrees) ;
        //vueTexte = findViewById (R.id.activity_choix_seance_enregistrees_vueTexte) ;

        // Récupération de l'instance de Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Opérations relatives à la liste de séances
        // 1. RecyclerView
        vue = findViewById (R.id.activity_choix_seance_enregistrees_listeSeances) ;
        vue.setHasFixedSize(true) ;
        // 2. LayoutManager
        gestDisp = new LinearLayoutManager (this) ;
        vue.setLayoutManager(gestDisp) ;
        // 3. Adaptateur
        adaptateur = new AdaptateurDeSeance(null, this) ;
        vue.setAdapter(adaptateur) ;

        afficheSeancesUtilisateur() ;  // chargement des données, création de l'adaptateur et remplissage de celui-ci avec la liste
    }


    public void afficheSeancesUtilisateur() {

        // Classe asynchrone permettant de récupérer les séances enregistrées dans la base par les utilisateurs
        class GetSeancesUtilisateur extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground (Void... voids) {
                List<Seance> seancesUtilisateur = cbdd.getAppDatabase().seanceDao().getCategorie("utilisateur") ;
                return seancesUtilisateur ;
            }

            @Override
            protected void onPostExecute (List<Seance> seancesUtilisateur) {
                super.onPostExecute(seancesUtilisateur) ;

                adaptateur.setSeances(seancesUtilisateur) ;
                adaptateur.notifyDataSetChanged() ;
            }
        }

        // Création d'un objet de type GetLast et execution de la demande asynchrone
        GetSeancesUtilisateur gsu = new GetSeancesUtilisateur() ;
        gsu.execute() ;
    }
}
