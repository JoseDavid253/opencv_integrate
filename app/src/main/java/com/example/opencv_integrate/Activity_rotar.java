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
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Activity_rotar extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;

    private Button btn_Original;
    private Button btn_rotate;
    private Mat imageMat;

    private double rotationAngle = 90.0; // Ángulo de rotación inicial

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotar);

        imageView = findViewById(R.id.imageViewRGB8);

        btn_Original = findViewById(R.id.buttonRGB_Original);
        btn_rotate = findViewById(R.id.btn_conver_rotate);

        // Aquí se recibe la ruta de la imagen y se crea una instancia local para su uso
        Intent intent = getIntent();
        if (intent != null) {
            String imagePath = intent.getStringExtra("imagen");
            if (imagePath != null) {
                // Cargar el Bitmap desde la ruta del archivo
                bitmap = BitmapFactory.decodeFile(imagePath);

                // Mostrar el Bitmap en un ImageView u otra vista
                imageView.setImageBitmap(bitmap);
                imageMat = new Mat();
                Utils.bitmapToMat(bitmap, imageMat);
            }
        }

        btn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rotar la imagen
                Mat rotatedImage = new Mat();
                Point center = new Point(imageMat.cols() / 2, imageMat.rows() / 2);
                Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, rotationAngle, 1.0);
                Imgproc.warpAffine(imageMat, rotatedImage, rotationMatrix, imageMat.size());

                // Convertir la imagen rotada a Bitmap
                Bitmap rotatedBitmap = Bitmap.createBitmap(rotatedImage.cols(), rotatedImage.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(rotatedImage, rotatedBitmap);

                // Mostrar la imagen rotada en el ImageView
                imageView.setImageBitmap(rotatedBitmap);

                // Actualizar el ángulo de rotación para la próxima vez que se presione el botón
                rotationAngle += 90.0;
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
