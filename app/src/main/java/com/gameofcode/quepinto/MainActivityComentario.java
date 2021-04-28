package com.gameofcode.quepinto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityComentario extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtPersona,txtFecha,txtcomentario;
    ImageButton share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_comentario);

        txtPersona =(TextView)findViewById(R.id.txtpersona);
        txtcomentario =(TextView)findViewById(R.id.comcomentario);
        txtFecha =(TextView)findViewById(R.id.comfecha);

    }

}