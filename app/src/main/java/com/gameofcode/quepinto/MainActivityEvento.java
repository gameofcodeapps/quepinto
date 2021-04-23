package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha,txtdscEvento,txtOrganizador,txtMapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_evento);
        imgEvento =(ImageView)findViewById(R.id.imgEvento);
        txtNomEvento =(TextView)findViewById(R.id.TxtNomEvento);
        txtFecha =(TextView)findViewById(R.id.txtFecha);
        txtdscEvento= (TextView)findViewById(R.id.txtdscEvento);
        txtOrganizador = (TextView)findViewById(R.id.txtOrganizador);
        txtMapa = (TextView)findViewById(R.id.txtMapa);

        imgEvento.setImageResource(getIntent().getIntExtra("imagename",0));
        txtNomEvento.setText(getIntent().getStringExtra("header"));
        txtFecha.setText(getIntent().getStringExtra("fecha"));
        txtdscEvento.setText(getIntent().getStringExtra("desc"));
        txtOrganizador.setText(getIntent().getStringExtra("organizador"));
        txtMapa.setText(getIntent().getStringExtra("mapa"));
    }

}