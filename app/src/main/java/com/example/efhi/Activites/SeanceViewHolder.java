package com.example.efhi.Activites ;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.Modele.MonApplication;
import com.example.efhi.R;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SeanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Attribut statique (LOGGER)
    private static final Logger LOGGER = Logger.getAnonymousLogger() ;
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$s] %4$-10s | (%3$s) %2$-15s | %5$s\n") ;
        LOGGER.setLevel(Level.INFO) ;
    }

    // Attributs
    private final AppCompatActivity activite ;
    private Seance seance ;
    private TextView vueTexteTitre ;
    private TextView vueTexteDescription ;

    public SeanceViewHolder (View itemView, AppCompatActivity activite) {
        super(itemView) ;
        this.activite = activite ;
        vueTexteTitre = itemView.findViewById(R.id.element_seance_titre) ;
        vueTexteDescription = itemView.findViewById(R.id.element_seance_description) ;
        itemView.setOnClickListener(this) ;
    }

    public void afficher (Seance seance) {
        this.seance = seance ;
        // Titre
        String titre = seance.getTitre().isEmpty() ? "Seance n°" + this.getItemId() : seance.getTitre() ;
        vueTexteTitre.setText(titre) ;
        // Description
        vueTexteDescription.setText("Description pour l'instant en dur") ;
    }

    @Override
    public void onClick (View view) {
        LOGGER.log(Level.INFO, "onClick - SeanceViewHolder") ;
        ((MonApplication) activite.getApplication()).setSeance(seance) ;
        Intent intention = new Intent (activite, ChoixSeanceDerniereSeanceActivity.class) ;
        activite.startActivity(intention) ;
    }
}
