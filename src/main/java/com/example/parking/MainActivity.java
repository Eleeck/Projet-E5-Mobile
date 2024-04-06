package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button connexion;
    private Button inscrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.connexion = findViewById(R.id.connexion);
        this.connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this, Connexion.class);
                startActivity(reg);

            }
        });

        this.inscrip = findViewById(R.id.inscrip);
        this.inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inscribe = new Intent(MainActivity.this, Inscription.class);
                startActivity(inscribe);
            }
        });
    }
}