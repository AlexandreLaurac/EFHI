package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.efhi.R;

public class SeanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_seance) ;
    }

    public void clicBoutonPageSuivante (View view) {
        Intent intention = new Intent (SeanceActivity.this, SeanceConclusionActivity.class) ;
        startActivity (intention) ;
    }
}