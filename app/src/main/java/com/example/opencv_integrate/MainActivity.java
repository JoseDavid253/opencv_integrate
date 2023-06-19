package com.example.opencv_integrate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button btn_Galeria;
    private ImageView imageViewCamera;
    private ImageView imageView;
    private ImageView imageViewRGB;
    private Bitmap bitmap;
    private int GALERIA_CODE = 100;
    private int CAMARA_CODE = 101;
    private String imagePath;
    private Mat mat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(OpenCVLoader.initDebug()){
            //Toast.makeText(this, "Hola mundo", Toast.LENGTH_SHORT).show();
        }

        imageViewCamera = findViewById(R.id.imageViewBtn);
        btn_Galeria = findViewById(R.id.btn_Galeria);
        imageView = findViewById(R.id.imageView);
        imageViewRGB = findViewById(R.id.imageViewRGB);

        btn_Galeria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALERIA_CODE);
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMARA_CODE);
            }
        });
        //Este metodo es para mandar informacion de una actividad a otra, en este caso un path donde se guardo la imagen con el metodo saveBitmap
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity3.class);
                //Intent intent2 = new Intent(MainActivity.this, MainActivity3.class);

                intent.putExtra("imagen", imagePath);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALERIA_CODE && data!= null){
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);
                imagePath = saveBitmap(bitmap);
                //imageViewRGB.setImageBitmap(bitmap);

                mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);

                //Escala de grises
                /*Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);

                Utils.matToBitmap(mat, bitmap);
                imageView.setImageBitmap(bitmap)
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
                Utils.matToBitmap(mat, bitmap);*/
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        if(requestCode == CAMARA_CODE && data != null){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imagePath = saveBitmap(bitmap);
            //imageViewRGB.setImageBitmap(bitmap);
            mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);
        }
    }

    private String saveBitmap(Bitmap bitmap){
        // Obtener el directorio de almacenamiento externo
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crear un nombre de archivo único para el Bitmap
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timestamp + ".jpg";

        // Crear el archivo en el directorio de almacenamiento externo
        File file = new File(directory, fileName);

        // Guardar el Bitmap en el archivo
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Devolver la ruta del archivo
        return file.getAbsolutePath();
    }

    public void getPermission(){
        if(checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new  String[]{Manifest.permission.CAMERA},102);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permisions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode, permisions, grantResult);

        if(requestCode == 102 && grantResult.length > 0){
            if(grantResult[0] != PackageManager.PERMISSION_GRANTED){
                getPermission();
            }
        }
    }

    //public void loadImagenesLayout(View v){ setContentView(R.layout.vista_imagenes);}
}