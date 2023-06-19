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

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Activity_grises extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private Button btn_grises;
    private Button btn_Original;
    private Mat greyImage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grises);

        imageView = findViewById(R.id.imageViewRGB5);
        btn_grises = findViewById(R.id.btn_conver_grises);
        btn_Original = findViewById(R.id.buttonRGB_Original);


        //Aqui se resive el path y se crea otro de manera local para su uso
        Intent intent = getIntent();
        if (intent != null) {
            String imagePath = intent.getStringExtra("imagen");
            if (imagePath != null) {
                // Cargar el Bitmap desde la ruta del archivo
                bitmap = BitmapFactory.decodeFile(imagePath);

                // Mostrar el Bitmap en un ImageView u otra vista
                imageView.setImageBitmap(bitmap);
            }
        }


        btn_grises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                greyImage = new Mat();
                Utils.bitmapToMat(bitmap, greyImage);
                //Core.split(greyImage, channels);

                Mat gGrises = new Mat();
                Imgproc.cvtColor(greyImage, gGrises, Imgproc.COLOR_BGR2GRAY);

                Bitmap grayBitmap = Bitmap.createBitmap(gGrises.cols(), gGrises.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(gGrises, grayBitmap);
                imageView.setImageBitmap(grayBitmap);
            }
        });

        btn_Original.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(bitmap);
            }
        });

    }
}
