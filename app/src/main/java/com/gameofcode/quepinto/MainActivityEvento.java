package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.EventoPresenter;
import com.gameofcode.quepinto.presentadores.MainPresenter;
import com.gameofcode.quepinto.services.myadaptercomentario;
import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainActivityEvento extends AppCompatActivity {
    ImageView imgEvento;
    TextView txtNomEvento,txtFecha,txtdscEvento,txtOrganizador,txtMapa, verMapa;
    ImageButton share,ticket,ride,fav;
    String _url = "https://tickantel.com.uy/inicio/?0";
    String _url2 = "https://www.uber.com/uy/es/ride/";
    EventoDTO eventoDTO;


    int i,auxindex,id,idusuario,auxindex2;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr,fecha,usuario,comentario;
    ImageView imageView;
    myadaptercomentario adapter;
    public int idEvento;
    private List<EventoDTO> eventos = null;
    private  List<EventoDTO> eventoDTOS;
    RecyclerView rcv;
    private  List<ComentarioDTO> comentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_evento);



        //placing toolbar in place of actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Qué Pintó?");
        setSupportActionBar(toolbar);
      //  cargartool();


        imgEvento =(ImageView)findViewById(R.id.imgEvento);
        txtNomEvento =(TextView)findViewById(R.id.TxtNomEvento);
        txtFecha =(TextView)findViewById(R.id.txtFecha);
        txtdscEvento= (TextView)findViewById(R.id.txtdscEvento);
        txtOrganizador = (TextView)findViewById(R.id.txtOrganizador);
        txtMapa = (TextView)findViewById(R.id.txtMapa);
        verMapa = (TextView)findViewById(R.id.verMapa);
        fav =(ImageButton)findViewById(R.id.imageButton3);
        rcv= findViewById(R.id.recview);
        //En caso de que lo vea una persona que no tenga usuario
        deshabilitarAccionesSiUsuarioNoLogeado();

        inicializar();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventoDTO eventoFavorito = new EventoDTO();
                eventoFavorito.setId(idEvento);
                IEventoPresenter presenter = new EventoPresenter();
                String mensaje;
                if(saberSiEventoEsFavorito()){
                    mensaje = "Quitando de favorito...";
                }else{
                    mensaje="Agregando Favorito...";

                }
                ProgressDialog progressDialog= ProgressDialog.show(view.getContext(), "",
                        mensaje, true);
                /////////////////////////////////////////////////////////////////
                new Thread(new Runnable() {
                    boolean resultado=false;
                    @Override
                    public void run() {

                        ////////Se ejecuta en segundo plano//////////////////////
                        if(saberSiEventoEsFavorito()){

                            resultado= presenter.borrarFavorito(eventoFavorito);

                        }else{
                            resultado = presenter.agregarFavorito(eventoFavorito);


                        }
                        ////////////////////////////////////////////////////////
                        //Se ejecuta al terminar la tarea en segundo plano
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(saberSiEventoEsFavorito()){
                                    if (resultado){
                                        fav.setBackgroundColor(getResources().getColor(R.color.fav));
                                    }else{
                                        Toast.makeText(view.getContext(),"Error al quitar favorito, intente nuevamente",Toast.LENGTH_LONG).show();
                                    }

                                }else{
                                    if (resultado){
                                        fav.setBackgroundColor(getResources().getColor(R.color.gray));
                                    }else{
                                        Toast.makeText(view.getContext(),"Error al agregar favorito, intente nuevamente",Toast.LENGTH_LONG).show();
                                    }
                                }
                                progressDialog.dismiss();

                            }
                        });
                        /////////////////////////////////////////////////////////
                    }
                }).start();




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
        txtMapa=(TextView)findViewById(R.id.txtMapa);
        verMapa=(TextView)findViewById(R.id.verMapa);
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

        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _direccionaux = getIntent().getStringExtra("mapa").replaceAll(" ","+");
                String _mapaUrl= "https://www.google.com.uy/maps/search/"+_direccionaux;
                Uri _link = Uri.parse(_mapaUrl);
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

        if(UsuarioModel.getInstance().getUsuarioLogeado()!=null){
            if(saberSiEventoEsFavorito()){
                fav.setBackgroundColor(getResources().getColor(R.color.fav));
            }
        }



    }

    private void deshabilitarAccionesSiUsuarioNoLogeado(){
        if(UsuarioModel.getInstance().getUsuarioLogeado()==null){

            TextView tvEnviarComentario = findViewById(R.id.txtEnviarComentarios);
            EditText etComentario = (EditText) findViewById(R.id.escribirComentario);
            Button btnAgregarComentario = (Button) findViewById(R.id.btnAgregarComentario);
            fav.setVisibility(View.INVISIBLE);
            tvEnviarComentario.setVisibility(View.INVISIBLE);
            etComentario.setVisibility(View.INVISIBLE);
            btnAgregarComentario.setVisibility(View.INVISIBLE);
        }
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
                        progressDialog.dismiss();
                        adapter = new myadaptercomentario(holder,getApplicationContext());
                        rcv.setHasFixedSize(true);
                        rcv.setAdapter(adapter);

                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
                        rcv.setLayoutManager(gridLayoutManager);

                    }
                });
            }

        }).start();

    }

    public ArrayList<ComentarioDTO> dataqueue()
    {
        ArrayList<ComentarioDTO> holder=new ArrayList<>();
        EventoModel instance = EventoModel.getInstance();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(idEvento);
        comentarios  = instance.obtenerComentariosDeEvento(eventoDTO);
        for (ComentarioDTO c:comentarios) {
            ComentarioDTO ob2 = new ComentarioDTO(
            c.getId(),
            c.getComentario(),
            c.getIdUsuario(),
            c.getFecha(),
            idEvento,
            c.getUsuario());

                holder.add(ob2);
            }

         return holder;
        }

        private boolean saberSiEventoEsFavorito(){
            UsuarioModel instance = UsuarioModel.getInstance();
            List<EventoDTO> eventoFavoritos = instance.getUsuarioLogeado().getEventoFavoritos();
            boolean esFavorito = false;
            for( EventoDTO evento : eventoFavoritos){
                if(evento.getId()==this.idEvento){
                    esFavorito=true;
                }
            }
            return esFavorito;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UsuarioModel.getInstance().getUsuarioLogeado()==null){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return true;
        }else{
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu, menu);
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.irlogeo:
                Intent irlogeo = new Intent(this,MainActivityRegUsuario.class);
                startActivity(irlogeo);
                break;

            case R.id.itSalir:
                Intent salir = new Intent(this,MainActivity.class);
                startActivity(salir);
                break;
            case R.id.irAcerca:
                Intent acercade = new Intent(this,MainActivityAcercade.class);
                startActivity(acercade);
                break;
            case R.id.menuC:
                Intent micuenta = new Intent(this,MainActivityPerfil.class);
                startActivity(micuenta);
                break;

            case R.id.menuF:
                Intent favoritos = new Intent(this,MainActivityFavoritos.class);
                startActivity(favoritos);
                break;

            case R.id.mencCr:
                Intent creevento = new Intent(this,MainActivityRegEvento.class);
                startActivity(creevento);
                break;
            case R.id.menuL:
                //So cierra sesion borro los datos grabados
                UsuarioModel.getInstance().borrarUsuarioLogeado(getApplicationContext());
                Intent logout = new Intent(this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.menuA:
                Intent acerca = new Intent(this,MainActivityAcercade.class);
                startActivity(acerca);
                break;
            case R.id.menuM:
                Intent miseventos = new Intent(this,MainActivityMisEventosCreados.class);
                startActivity(miseventos);
                break;
        }
        return true;
    }

}

