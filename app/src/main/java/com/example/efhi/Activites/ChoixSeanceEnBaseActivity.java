package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.efhi.Modele.BDD.DatabaseClient;
import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.R;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChoixSeanceEnBaseActivity extends AppCompatActivity {

    // Attributs de classe
    public static final String CATEGORIE_SEANCES = "categorie_seances" ;
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
    private DatabaseClient cbdd ;
    private String categorieSeances ;
        // Attributs liés à l'affichage des séances sous forme de liste
    private RecyclerView vue ;
    private AdaptateurDeSeance adaptateur ;
    private RecyclerView.LayoutManager gestDisp ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_choix_seance_en_base) ;

        categorieSeances = getIntent().getStringExtra(CATEGORIE_SEANCES) ;

        // Titre
        TextView vueTexteTitre = findViewById (R.id.activity_choix_seance_en_base_titre) ;
        String titre ;
        switch (categorieSeances) {
            case "preset" :
                titre = "Séances prédéfinies" ;
                break ;
            case "utilisateur" :
                titre = "Séances enregistrées" ;
                break ;
            default :
                titre = "Séances disponibles" ;
        }
        vueTexteTitre.setText(titre) ;

        // Récupération de l'instance de Databaseclient
        cbdd = DatabaseClient.getInstance(getApplicationContext()) ;

        // Opérations relatives à la liste de séances
        // 1. RecyclerView
        vue = findViewById (R.id.activity_choix_seance_en_base_listeSeances) ;
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
        class GetSeances extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground (Void... voids) {
                return cbdd.getAppDatabase().seanceDao().getCategorie(categorieSeances) ;
            }

            @Override
            protected void onPostExecute (List<Seance> seances) {
                super.onPostExecute(seances) ;
                adaptateur.setSeances(seances) ;
                adaptateur.notifyDataSetChanged() ;
            }
        }

        // Création d'un objet de type GetLast et execution de la demande asynchrone
        GetSeances gs = new GetSeances() ;
        gs.execute() ;
    }
}
