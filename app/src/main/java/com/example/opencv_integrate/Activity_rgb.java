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

public class Activity_rgb extends AppCompatActivity {

    private ImageView imageView;
    private Button btn_Rojo;
    private Button btn_Verde;
    private Button btn_Azul;
    private Button btn_Original;
    private Mat rgbImage;
    private List<Mat> channels = new ArrayList<>();
    private Bitmap bitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canales_rgb);

        imageView  = findViewById(R.id.imageViewRGB2);
        btn_Rojo = findViewById(R.id.buttonRGB_Rojo);
        btn_Verde = findViewById(R.id.buttonRGB_Verde);
        btn_Azul = findViewById(R.id.buttonRGB_Azul);
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

        rgbImage = new Mat();
        Utils.bitmapToMat(bitmap, rgbImage);
        Core.split(rgbImage, channels);

        Mat rChannel = channels.get(0);
        Mat gChannel = channels.get(1);
        Mat bChannel = channels.get(2);

        Mat rChannelRGBA = new Mat();
        Mat gChannelRGBA = new Mat();
        Mat bChannelRGBA = new Mat();

        btn_Rojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imgproc.cvtColor(rChannel, rChannelRGBA, Imgproc.COLOR_GRAY2RGBA);
                Bitmap rGrayBitmap = Bitmap.createBitmap(rChannelRGBA.cols(), rChannelRGBA.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rChannelRGBA, rGrayBitmap);
                imageView.setImageBitmap(rGrayBitmap);
            }
        });

        btn_Verde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imgproc.cvtColor(gChannel, gChannelRGBA, Imgproc.COLOR_GRAY2RGBA);
                Bitmap gGrayBitmap = Bitmap.createBitmap(gChannelRGBA.cols(), gChannelRGBA.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(gChannelRGBA, gGrayBitmap);
                imageView.setImageBitmap(gGrayBitmap);
            }
        });

        btn_Azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imgproc.cvtColor(bChannel, bChannelRGBA, Imgproc.COLOR_GRAY2RGBA);
                Bitmap bGrayBitmap = Bitmap.createBitmap(bChannelRGBA.cols(), bChannelRGBA.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(bChannelRGBA, bGrayBitmap);
                imageView.setImageBitmap(bGrayBitmap);
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