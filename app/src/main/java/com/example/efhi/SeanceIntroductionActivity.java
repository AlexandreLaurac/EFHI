package com.example.efhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeanceIntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_seance_introduction) ;
    }

    public void clicBoutonCommencer (View view) {
        Intent intention = new Intent (SeanceIntroductionActivity.this, SeanceActivity.class) ;
        startActivity (intention) ;
    }
}