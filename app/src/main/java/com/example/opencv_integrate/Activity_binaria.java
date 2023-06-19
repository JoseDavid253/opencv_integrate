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
import org.opencv.imgproc.Imgproc;

public class Activity_binaria extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private Button btn_binaria;
    private Button btn_Original;
    private Mat binaryImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binaria);

        imageView = findViewById(R.id.imageViewRGB4);
        btn_binaria = findViewById(R.id.btn_conver_binaria);
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
        btn_binaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binaryImage = new Mat();
                Utils.bitmapToMat(bitmap, binaryImage);
                //Core.split(greyImage, channels);

                Mat bBinary = new Mat();
                Imgproc.cvtColor(binaryImage, bBinary, Imgproc.COLOR_RGB2GRAY);
                Imgproc.threshold(bBinary, bBinary, 128, 255, Imgproc.THRESH_BINARY);
                Bitmap binaryBitmap = Bitmap.createBitmap(bBinary.cols(), bBinary.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(bBinary, binaryBitmap);
                imageView.setImageBitmap(binaryBitmap);
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
