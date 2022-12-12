package com.example.efhi.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.efhi.R;

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