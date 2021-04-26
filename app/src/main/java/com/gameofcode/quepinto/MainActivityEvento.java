package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivityEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha,txtdscEvento,txtOrganizador,txtMapa;
    ImageButton share,ticket,ride;
    TextView verCom,ingCom;
    String _url = "https://tickantel.com.uy/inicio/?0";
    String _url2 = "https://www.uber.com/uy/es/ride/";
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
        verCom = (TextView)findViewById(R.id.txtVerComentarios);
        ingCom = (TextView)findViewById(R.id.escribirComentario);


        //imgEvento.setImageResource(getIntent().getIntExtra("imagename",0));
        txtNomEvento.setText(getIntent().getStringExtra("header"));
        txtFecha.setText(getIntent().getStringExtra("fecha"));
        txtdscEvento.setText(getIntent().getStringExtra("desc"));
        txtOrganizador.setText(getIntent().getStringExtra("organizador"));
        txtMapa.setText(getIntent().getStringExtra("mapa"));
        txtdscEvento.setMovementMethod(new ScrollingMovementMethod());

        String string = getIntent().getStringExtra("imagen");
        string=string.replace("http:/", "https:/");
        Picasso.with(imgEvento.getContext()).load(string).into(imgEvento);


        ride =(ImageButton)findViewById(R.id.ride);
        ticket=(ImageButton)findViewById(R.id.Ticket);
        share=(ImageButton)findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody="Accede a nuestra aplicación desde aquí; http://quepinto.pythonanywhere.com/home/ficha/"+getIntent().getIntExtra("idEvento",0);
                String shareSub="QuePintó?";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

                startActivity(Intent.createChooser(shareIntent,"Compartir usando:"));
            }
        });

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri _link = Uri.parse(_url);
                Intent i  =new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri _link = Uri.parse(_url2);
                Intent i  =new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

    }

}