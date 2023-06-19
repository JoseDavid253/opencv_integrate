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
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Activity_contornos extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private Button btn_contornos;
    private Button btn_Original;
    private Mat contorImage, imageMat;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contornos);

        imageView = findViewById(R.id.imageViewRGB7);
        btn_contornos = findViewById(R.id.btn_conver_contornos);
        btn_Original = findViewById(R.id.buttonRGB_Original);


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

        btn_contornos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat grayImage = new Mat();
                Imgproc.cvtColor(imageMat, grayImage, Imgproc.COLOR_BGR2GRAY);

                Mat blurredImage = new Mat();
                Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);

                Mat cannyEdges = new Mat();
                Imgproc.Canny(blurredImage, cannyEdges, 80, 100);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(cannyEdges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                Mat resultImage = imageMat.clone();
                Imgproc.drawContours(resultImage, contours, -1, new Scalar(0, 255, 0), 2);

                Bitmap resultBitmap = Bitmap.createBitmap(resultImage.cols(), resultImage.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(resultImage, resultBitmap);
                imageView.setImageBitmap(resultBitmap);
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