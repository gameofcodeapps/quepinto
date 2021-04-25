package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityListadoEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha;
    ImageButton share;
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
        share=(ImageButton)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody="Accede a nuestra aplicación desde aquí; http://quepinto.pythonanywhere.com";
                String shareSub="QuePintó?";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

                startActivity(Intent.createChooser(shareIntent,"Compartir usando:"));
            }
        });
    }

}