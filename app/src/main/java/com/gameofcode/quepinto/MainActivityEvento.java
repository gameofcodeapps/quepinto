package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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

import com.gameofcode.quepinto.DTO.ComentarioDTO;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;
import com.gameofcode.quepinto.interfaces.IEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.presentadores.EventoPresenter;
import com.gameofcode.quepinto.services.myadaptercomentario;
import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainActivityEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha,txtdscEvento,txtOrganizador,txtMapa;
    ImageButton share,ticket,ride,fav;
    String _url = "https://tickantel.com.uy/inicio/?0";
    String _url2 = "https://www.uber.com/uy/es/ride/";
    EventoDTO eventoDTO;

    int i,auxindex,id,idusuario,auxindex2;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr,fecha,usuario,comentario;
    ImageView imageView;
    myadaptercomentario adapter;
    private int idEvento;
    private List<EventoDTO> eventos = null;
    private  List<EventoDTO> eventoDTOS;
    RecyclerView rcv;
    private  List<ComentarioDTO> comentarios;
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
        fav =(ImageButton)findViewById(R.id.imageButton3);
        rcv= findViewById(R.id.recview);


        inicializar();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav.setBackgroundColor(getResources().getColor(R.color.fav));
            }
        });

        //imgEvento.setImageResource(getIntent().getIntExtra("imagename",0));
        txtNomEvento.setText(getIntent().getStringExtra("header"));
        txtFecha.setText(getIntent().getStringExtra("fecha"));
        txtdscEvento.setText(getIntent().getStringExtra("desc"));
        txtOrganizador.setText(getIntent().getStringExtra("organizador"));
        txtMapa.setText(getIntent().getStringExtra("mapa"));
        txtdscEvento.setMovementMethod(new ScrollingMovementMethod());
        idEvento = getIntent().getIntExtra("idEvento",0);

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
                                        inicializar();
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

    private void inicializar(){
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Cargando comentarios...", true);
        new Thread(new Runnable() {
            @Override

            public void run() {
                ArrayList<ComentarioDTO> holder = dataqueue();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new myadaptercomentario(holder,getApplicationContext());
                        rcv.setHasFixedSize(true);
                        rcv.setAdapter(adapter);

                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
                        rcv.setLayoutManager(gridLayoutManager);
                        progressDialog.dismiss();
                    }
                });
            }

        }).start();

    }

    public ArrayList<ComentarioDTO> dataqueue()
    {
        ArrayList<ComentarioDTO> holder=new ArrayList<>();

        //traigo last index
        traerLastIndex();

        while (auxindex2 == 0){

        }

        // comienzo loop I=1 hasta last
        for(i=0; i<auxindex2; i++){
            ComentarioDTO ob2 = new ComentarioDTO(id,comentario,idusuario,fecha,idEvento,usuario);

            obtenerComentarios();

            while(comentario == null){

            }

            ob2.setId(id);
            ob2.setComentario(comentario);
            ob2.setIdUsuario(idusuario);
            ob2.setFecha(fecha);
            ob2.setUsuario(usuario);
            holder.add(ob2);
/*
            ob.setHeader(auxNom);
            ob1.setDesc(auxdsc);
            ob1.setImgname(R.drawable.banda1);
            ob1.setFecha(auxfch);
            ob1.setOrganizador(auxorg);
            ob1.setTxtmapa(auxdir);
            ob1.setUrlimagen(auximg);
            ob1.setEsFavorito(true);
            //Agregado para compartir web
            ob1.setId(idEvento);

            ///Alternativa a cargar imagen/////
            //ob1.setImgen(imagen);
            ////////////////////////////////////

            holder.add(ob1);*/
            comentario = null;
        }


        return holder;
    }

    private void obtenerComentarios(){
        //Se ejecuta antes de la tarea en segundo plano

        Log.i("Eventos",String.valueOf(comentarios.get(i).getId()));
        id = comentarios.get(i).getId();
        Log.i("Eventos",String.valueOf(comentarios.get(i).getFecha()));
        fecha = String.valueOf(comentarios.get(i).getFecha());
        Log.i("Eventos",String.valueOf(comentarios.get(i).getFecha()));
        idusuario = comentarios.get(i).getIdUsuario();
        Log.i("Eventos",String.valueOf(comentarios.get(i).getFecha()));
        usuario = String.valueOf(comentarios.get(i).getUsuario());
        Log.i("Eventos",String.valueOf(comentarios.get(i).getComentario()));
        comentario = String.valueOf(comentarios.get(i).getComentario());

      //  idEvento = eventoDTOS.get(i).getId();

        ///Alternativa a cargar imagen/////
        //imagen=eventoDTOS.get(i).getImagenEventoBMP();
        //////////////////////////////////

        //Se ejecuta al terminar la tarea en segundo plano
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(),eventoDTOS.size(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void traerLastIndex(){
        //Se ejecuta antes de la tarea en segundo plano

        //new Thread(new Runnable() {
        // @Override
        //public void run() {
        //Se ejecuta en segundo plano

        EventoModel instance = EventoModel.getInstance();

            eventos = instance.obtenerTodosLosEventosHabilitados();
            auxindex = eventos.size();

        while (auxindex == 0){

        }

        // comienzo loop I=1 hasta last
        for(int j=0; j<auxindex; j++) {

            if (eventos.get(j).getId() == idEvento){
                comentarios  = instance.obtenerComentariosDeEvento(eventos.get(j));
                auxindex2 =comentarios.size();
                j = auxindex;
            }

        }
        //Se ejecuta al terminar la tarea en segundo plano
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(getApplicationContext(),eventoDTOS.size(),Toast.LENGTH_LONG).show();
            }
        });
    }

}