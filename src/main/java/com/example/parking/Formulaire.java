package com.example.parking;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Formulaire extends AppCompatActivity {

    private EditText editTextPrenom;
    private EditText editTextNom;
    private EditText editTextAddress;
    private EditText editTextTime;
    private EditText editTextVehicleCount;
    private EditText editTextPhoneNumber;
    private EditText editTextLicensePlate;
    private Button buttonConfirmer;
    private Button buttonAjouterPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constat);

        // Initialisation des vues
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextNom = findViewById(R.id.editTextNom);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextTime = findViewById(R.id.editTextTime);
        editTextVehicleCount = findViewById(R.id.editTextVehicleCount);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextLicensePlate = findViewById(R.id.editTextLicensePlate);
        buttonConfirmer = findViewById(R.id.confirmer);
        buttonAjouterPhoto = findViewById(R.id.ajouter);

        // Ajouter l'action pour le bouton "Confirmer"
        buttonConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les informations saisies dans les champs
                String prenom = editTextPrenom.getText().toString();
                String nom = editTextNom.getText().toString();
                String address = editTextAddress.getText().toString();
                String time = editTextTime.getText().toString();
                String vehicleCount = editTextVehicleCount.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String licensePlate = editTextLicensePlate.getText().toString();

                // Envoi des données à la base de données via une requête HTTP ou autre méthode

                // Exemple d'affichage d'un message de succès
                Toast.makeText(Formulaire.this, "Formulaire soumis avec succès!", Toast.LENGTH_SHORT).show();
            }
        });

        // Ajouter des actions pour le bouton "Ajouter photo" si nécessaire
    }
}

