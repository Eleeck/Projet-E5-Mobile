package com.example.parking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Inscription extends AppCompatActivity {

    private EditText editMail, Nom2, editPrenom3, TextPassword, TextPhone;
    private Button inscrit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        this.editMail = findViewById(R.id.editMail);
        this.Nom2 = findViewById(R.id.Nom2);
        this.editPrenom3 = findViewById(R.id.editPrenom3);
        this.TextPassword = findViewById(R.id.TextPassword);
        this.TextPhone = findViewById(R.id.TextPhone);
        this.inscrit = findViewById(R.id.inscrit);

        inscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editMail.getText().toString();
                String nom = Nom2.getText().toString();
                String prenom = editPrenom3.getText().toString();
                String password = TextPassword.getText().toString();
                String telephone = TextPhone.getText().toString();

                if (email.isEmpty() || nom.isEmpty() || prenom.isEmpty() || password.isEmpty() || telephone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    InscriptionTask inscriptionTask = new InscriptionTask(Inscription.this);
                    inscriptionTask.execute(email, nom, prenom, password, telephone);
                }
            }
        });
    }

    class InscriptionTask extends AsyncTask<String, Void, Integer> {

        AlertDialog alertDialog;
        Context context;
        ProgressDialog progressDialog;

        InscriptionTask(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Inscription en cours... Veuillez patienter");
        }

        @Override
        protected Integer doInBackground(String... params) {
            String inscriptionUrl = "http://10.21.0.53/api/user/register.php";
            try {
                URL url = new URL(inscriptionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                        URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                        URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    return jsonObject.getInt("status");
                } else {
                    return HttpURLConnection.HTTP_BAD_REQUEST;
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return HttpURLConnection.HTTP_BAD_REQUEST;
            }
        }

        @Override
        protected void onPostExecute(Integer status) {
            progressDialog.dismiss();

            if (status == HttpURLConnection.HTTP_OK) {
                Toast.makeText(context, "Inscription réussie", Toast.LENGTH_SHORT).show();
                // Clear input fields after successful registration
                editMail.setText("");
                Nom2.setText("");
                editPrenom3.setText("");
                TextPassword.setText("");
                TextPhone.setText("");
            } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                Toast.makeText(context, "Erreur lors de l'inscription", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Erreur dans la connexion.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
