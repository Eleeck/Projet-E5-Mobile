package com.example.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Connexion extends AppCompatActivity {

    private EditText TextEmail , TextPassword;
    private Button connectButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        TextEmail = findViewById(R.id.editEmail);
        TextPassword = findViewById(R.id.editPassword);
        connectButton = findViewById(R.id.connect);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TextEmail.getText().toString().trim();
                String password = TextPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(Connexion.this, "Veuillez entrer votre identifiant", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(Connexion.this, "Veuillez entrer votre mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                new LoginTask(Connexion.this).execute(email, password);
            }
        });
    }

    private static class LoginTask extends AsyncTask<String, Void, Integer> {

        private Context context;

        LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            try {
                String loginUrl = "http://10.21.0.15/api/user/login.php";
                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                httpURLConnection.getOutputStream().write(data.getBytes());

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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
            if (status == HttpURLConnection.HTTP_OK) {
                Toast.makeText(context, "Connexion réussie", Toast.LENGTH_SHORT).show();
                // Rediriger vers une autre activité ici
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                Toast.makeText(context, "Identifiant invalide", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Erreur dans la connexion", Toast.LENGTH_LONG).show();
            }
        }
    }
}

