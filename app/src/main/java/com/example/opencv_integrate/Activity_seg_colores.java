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
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity_seg_colores extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private Button btn_rojos;
    private Button btn_azules;
    private Button btn_verdes;
    private Button btn_Original;
    private Mat segImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seg_colores);

        imageView = findViewById(R.id.imageViewRGB6);
        btn_rojos = findViewById(R.id.btn_seg_rojo);
        btn_azules = findViewById(R.id.btn_seg_azul);
        btn_verdes = findViewById(R.id.btn_seg_verde);
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
        btn_rojos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat image = new Mat();
                Utils.bitmapToMat(bitmap, image);
                Mat hsv_img = new Mat();
                Imgproc.cvtColor(image,hsv_img,Imgproc.COLOR_BGR2HSV);

                Scalar min_rojo = new Scalar(100,100,100);
                Scalar max_rojo = new Scalar(130,255,255);

                //azul
                //0,100,100
                //10,255,255
                Mat mRojo = new Mat();
                Core.inRange(hsv_img,min_rojo,max_rojo,mRojo);

                Mat objRojo = new Mat();
                Core.bitwise_and(image,image,objRojo,mRojo);

                Bitmap rojoBitmap = Bitmap.createBitmap(objRojo.cols(),objRojo.rows(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(objRojo,rojoBitmap);
                imageView.setImageBitmap(rojoBitmap);

            }
        });

        btn_azules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat image = new Mat();
                Utils.bitmapToMat(bitmap, image);
                Mat hsv_img = new Mat();
                Imgproc.cvtColor(image,hsv_img,Imgproc.COLOR_BGR2HSV);

                Scalar min_azul = new Scalar(0,100,100);
                Scalar max_azul = new Scalar(10,255,255);

                //azul
                //0,100,100
                //10,255,255
                Mat mAzul = new Mat();
                Core.inRange(hsv_img,min_azul,max_azul,mAzul);

                Mat objAzul = new Mat();
                Core.bitwise_and(image,image,objAzul,mAzul);

                Bitmap azulBitmap = Bitmap.createBitmap(objAzul.cols(),objAzul.rows(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(objAzul,azulBitmap);
                imageView.setImageBitmap(azulBitmap);

            }
        });

        btn_verdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat image = new Mat();
                Utils.bitmapToMat(bitmap, image);
                Mat hsv_img = new Mat();
                Imgproc.cvtColor(image,hsv_img,Imgproc.COLOR_BGR2HSV);

                Scalar min_verde = new Scalar(40,100,100);
                Scalar max_verde = new Scalar(80,255,255);

                //azul
                //0,100,100
                //10,255,255
                Mat mVerde = new Mat();
                Core.inRange(hsv_img,min_verde,max_verde,mVerde);

                Mat objVerde = new Mat();
                Core.bitwise_and(image,image,objVerde,mVerde);

                Bitmap verdeBitmap = Bitmap.createBitmap(objVerde.cols(),objVerde.rows(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(objVerde,verdeBitmap);
                imageView.setImageBitmap(verdeBitmap);

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
