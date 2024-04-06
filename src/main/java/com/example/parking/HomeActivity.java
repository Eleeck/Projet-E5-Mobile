package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button press , constat;
    private TextView accueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        String nom = getIntent().getStringExtra("my_name");
        this.accueil = findViewById(R.id.textView);
        this.accueil.setText( "Bienvenue " + nom);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        press = findViewById(R.id.get_data);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                String url = "http://localhost/api/test.php";
                //String url ="C:\\wamp64\\www\\api";

                StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Déclenché si on reçoit le contenu
                        try{
                            JSONArray jsa = new JSONArray(s);
                            List<Data> contenu = new ArrayList<>();
                            for(int i =0; i <jsa.length(); i++){
                                JSONObject jso = jsa.getJSONObject(i);
                                Data cur = new Data (
                                        jso.getString("Nom"),
                                        jso.getString("Prenom"),
                                        jso.getString("Email"),
                                        jso.getString("Password"),
                                        jso.getString("Telephone")
                                );
                                contenu.add(cur);
                            }
                            ListView l = findViewById(R.id.liste_data);
                            Home_Data_Adapter HDA = new Home_Data_Adapter(contenu, HomeActivity.this);
                            l.setAdapter(HDA);

                            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(HomeActivity.this, CommonData.class);
                                    Data donnee = (Data) parent.getAdapter().getItem(position);
                                    i.putExtra("Name", donnee.getNom() );
                                    i.putExtra("Mail", donnee.getEmail());
                                    i.putExtra("Body", donnee.getTel());

                                    startActivity(i);

                                }
                            });
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Déclenché en cas d'erreur
                        Toast.makeText(HomeActivity.this,"Erreur",Toast.LENGTH_SHORT).show();

                    }
                });

                queue.add(sr);

            }
        });


    }
}
