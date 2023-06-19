package com.example.opencv_integrate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity3 extends AppCompatActivity {
    private Button canales_rbg;
    private ImageView imageView;
    private Button conver_grises;
    private Button conver_binaria;
    private Button conver_seg;
    private Button conver_contornos;
    private Button conver_rotar;
    private Bitmap bitmap;
    private String imagePath;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        canales_rbg = findViewById(R.id.btn_canales_rgb);
        imageView = findViewById(R.id.imageViewRGB3);
        conver_grises = findViewById(R.id.btn_grises);
        conver_binaria = findViewById(R.id.btn_binaria);
        conver_seg = findViewById(R.id.btn_seg_colores);
        conver_contornos = findViewById(R.id.btn_det_contornos);
        conver_rotar = findViewById(R.id.btn_rotate);

        //Aqui se resive el path y se crea otro de manera local para su uso
        Intent intent = getIntent();

        if (intent != null) {
            imagePath = intent.getStringExtra("imagen");
            if (imagePath != null) {
                // Cargar el Bitmap desde la ruta del archivo
                bitmap = BitmapFactory.decodeFile(imagePath);

                // Mostrar el Bitmap en un ImageView u otra vista
                imageView.setImageBitmap(bitmap);
            }
        }
        canales_rbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_canales_rgb);
                Intent intent = new Intent(v.getContext(), Activity_rgb.class);
                //Intent intent2 = new Intent(MainActivity.this, MainActivity3.class);

                intent.putExtra("imagen", imagePath);
                startActivity(intent);
            }
        });

        conver_grises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(),Activity_grises.class);
                intent1.putExtra("imagen",imagePath);
                startActivity(intent1);
            }
        });

        conver_binaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(),Activity_binaria.class);
                intent2.putExtra("imagen",imagePath);
                startActivity(intent2);
            }
        });
        conver_seg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(),Activity_seg_colores.class);
                intent3.putExtra("imagen",imagePath);
                startActivity(intent3);
            }
        });
        conver_contornos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(v.getContext(),Activity_contornos.class);
                intent4.putExtra("imagen",imagePath);
                startActivity(intent4);
            }
        });

        conver_rotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(v.getContext(),Activity_rotar.class);
                intent5.putExtra("imagen",imagePath);
                startActivity(intent5);

            }
        });








    }

}


