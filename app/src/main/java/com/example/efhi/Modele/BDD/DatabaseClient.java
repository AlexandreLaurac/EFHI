package com.example.efhi.Modele.BDD;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class DatabaseClient {

    // Instance unique de la classe
    private static DatabaseClient instance ;

    // Objet représentant la base de données de l'application
    private AppDatabase appDatabase ;

    // Constructeur
    private DatabaseClient (final Context context) {

        // Création de l'objet appDatabase à l'aide du "Room database builder" :
        // EfhiBDD est le nom de la base de données
        // Remplissage de la BDD à la première création par appel de addCallback avec l'objet roomDatabaseCallback
        appDatabase = Room
                .databaseBuilder(context, AppDatabase.class, "EfhiBDD")
                .addCallback(roomDatabaseCallback)
                .build() ;
    }

    // Méthode statique retournant l'unique instance de DatabaseClient
    public static synchronized DatabaseClient getInstance (Context context) {
        if (instance == null) {
            instance = new DatabaseClient (context) ;
        }
        return instance ;
    }

    // Getter de l'objet appDatabase
    public AppDatabase getAppDatabase() {
        return appDatabase ;
    }

    // Objet permettant de remplir la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db) ;
        // Requêtes d'insertion initiales
        db.execSQL("INSERT INTO seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong, categorie, titre) VALUES (10, 2, 2, 15, 5, 10, \"preset\", \"Débutant\") ;") ;
        db.execSQL("INSERT INTO seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong, categorie, titre) VALUES (20, 2, 3, 20, 5, 10, \"preset\", \"Intermédiaire\") ;") ;
        db.execSQL("INSERT INTO seance (tpsPreparation, nbSequences, nbCycles, tpsTravail, tpsRepos, tpsReposLong, categorie, titre) VALUES (30, 3, 4, 30, 5, 20, \"preset\", \"Avancé\") ;") ;


        }
    };
}
