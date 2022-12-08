package com.example.efhi.Activites ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.efhi.Modele.BDD.Seance;
import com.example.efhi.R;


public class AdaptateurDeSeance extends RecyclerView.Adapter<SeanceViewHolder> {

    // Attributs
    private final AppCompatActivity activite ;
    private List<Seance> seances ;

    public AdaptateurDeSeance (List<Seance> seances, AppCompatActivity activite) {
        this.activite = activite ;
        this.seances = seances ;
    }

    public void setSeances (List<Seance> seances) {
        this.seances = seances ;
    }

    @NonNull
    @Override
    public SeanceViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View vue = inflater.inflate(R.layout.element_seance, parent, false) ;
        return new SeanceViewHolder (vue, activite) ;
    }

    @Override
    public void onBindViewHolder (@NonNull SeanceViewHolder holder, int position)
    {
        Seance seance = seances.get(position) ;
        holder.afficher(seance) ;
    }

    @Override
    public int getItemCount()
    {
        if (seances != null)
            return seances.size() ;
        return 0 ;
    }
}

