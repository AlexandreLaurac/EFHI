package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EcranAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView (R.layout.activity_ecran_accueil) ;
    }

    public void clicBoutonCommencer (View view) {
        Intent intention = new Intent (EcranAccueilActivity.this, ChoixSeanceMenuActivity.class) ;
        startActivity (intention) ;
    }
}