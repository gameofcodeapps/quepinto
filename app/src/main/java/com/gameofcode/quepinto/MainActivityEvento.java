package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gameofcode.quepinto.interfaces.IEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.presentadores.EventoPresenter;
import com.squareup.picasso.Picasso;

public class MainActivityEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha,txtdscEvento,txtOrganizador,txtMapa;
    ImageButton share,ticket,ride;
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

        //imgEvento.setImageResource(getIntent().getIntExtra("imagename",0));
        txtNomEvento.setText(getIntent().getStringExtra("header"));
        txtFecha.setText(getIntent().getStringExtra("fecha"));
        txtdscEvento.setText(getIntent().getStringExtra("desc"));
        txtOrganizador.setText(getIntent().getStringExtra("organizador"));
        txtMapa.setText(getIntent().getStringExtra("mapa"));
        txtdscEvento.setMovementMethod(new ScrollingMovementMethod());
        if(getIntent().getBooleanExtra("esFavorito",false)){
            Log.i("es favorito","seee");
        }

        Button btnAgregarComentario = (Button) findViewById(R.id.btnAgregarComentario);
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

        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etComentario = (EditText) findViewById(R.id.escribirComentario);
                String comentario = etComentario.getText().toString();
                if(!comentario.isEmpty()){
                    ProgressDialog progressDialog= ProgressDialog.show(v.getContext(), "",
                            "Agregando Comentario...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            IEventoPresenter eventoPresenter = new EventoPresenter();
                            boolean resultado = eventoPresenter.agregarComentario(getIntent().getIntExtra("idEvento",0),comentario);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if(resultado){
                                        Toast.makeText(getApplicationContext(),"Listo!",Toast.LENGTH_LONG);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Error al ingresar el comentario",Toast.LENGTH_LONG);
                                    }
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(),"El comentario esta vacio",Toast.LENGTH_LONG);
                }

            }
        });
    }

}