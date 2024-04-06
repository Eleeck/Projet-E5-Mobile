package com.example.parking;

import android.content.ActivityNotFoundException;

import android.content.Context;
import  android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CommonData extends AppCompatActivity {

    private TextView data_common_name;
    private TextView data_common_email;
    private TextView data_common_body;


    private Button photo_prise;
    private static final int CAMERA_REQUEST_CODE = 69;
    private ImageView ImageV;



  private String picPath = "\\Stockage interne\\DCIM\\Projet mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.data_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        String nom = getIntent().getStringExtra("Name");
        this.data_common_name = findViewById(R.id.data_name);
        this.data_common_name.setText( "Bonjour " + nom);

        String mail = getIntent().getStringExtra("Mail");
        this.data_common_email = findViewById(R.id.data_email);
        this.data_common_email.setText( "Mail : " + mail);

        String body = getIntent().getStringExtra("Body");
        this.data_common_body = findViewById(R.id.data_body);
        this.data_common_body.setText(body);

        photo_prise = findViewById(R.id.Capture);
        photo_prise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // Gérer le cas où aucune caméra n'est disponible
            Toast.makeText(CommonData.this, "Aucune application de caméra disponible", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Afficher l'image dans ImageView : fonctionne
                ImageView imageView = findViewById(R.id.click_image);
                imageView.setImageBitmap(imageBitmap);

                // Sauvegarder l'image dans le stockage interne : fails
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, imageFileName + ".jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    Log.d("ImagePath", file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

