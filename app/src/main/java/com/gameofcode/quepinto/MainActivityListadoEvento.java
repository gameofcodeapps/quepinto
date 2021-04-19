package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityListadoEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listado_evento);
        imgEvento =(ImageView)findViewById(R.id.imgEvento);
        txtNomEvento =(TextView)findViewById(R.id.TxtNomEvento);
        txtFecha =(TextView)findViewById(R.id.txtfchEve);

        imgEvento.setImageResource(getIntent().getIntExtra("Imagen Evento",0));
        txtNomEvento.setText(getIntent().getStringExtra("Evento"));
        txtFecha.setText(getIntent().getStringExtra("Fecha y Hora Evento"));
    }



}