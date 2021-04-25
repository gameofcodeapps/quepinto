package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainParaPruebas extends AppCompatActivity {
ImageView imgpruebas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_para_pruebas);

        imgpruebas = findViewById(R.id.imgpruebas);
        Picasso.with(this).load("https://su-noticias.com/wp-content/uploads/2019/11/10-mejores-hackers-historia-326x245.jpg").into(imgpruebas);
    }

}