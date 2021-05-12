package com.gameofcode.quepinto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.models.EventoModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuscarsinLogueo extends AppCompatActivity {
    RecyclerView rcv;
    int i,auxindex;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr,auxurlimg;
    ImageView imageView;
    myadapter adapter;
    Bitmap bmp;
    private int idEvento;
    private List<EventoDTO> eventos = null;


    ////Alternativa cargar imagen/////////
    //private Bitmap imagen;
    //////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_logueo);
        //accedo a toolbar
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
         rcv= findViewById(R.id.recview);

        //pongo Título
        toolbar2.setTitle("Qué Pintó?");


        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar2);

        SearchView simpleSearchView = (SearchView) findViewById(R.id.searchView);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                inicializar(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    inicializar("");
                }
                return false;
            }
        });

        inicializar("");

    }

    private void inicializar(String pBusqueda){
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Buscando Eventos...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Model> holder = dataqueue(pBusqueda);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new myadapter(holder,getApplicationContext());
                        rcv.setHasFixedSize(true);
                        rcv.setAdapter(adapter);

                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                        rcv.setLayoutManager(gridLayoutManager);
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.irlogeo:
                Intent micuenta = new Intent(this,MainActivityRegUsuario.class);
                startActivity(micuenta);
                break;

            case R.id.itSalir:
                Intent logout = new Intent(this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.irAcerca:
                Intent acercade = new Intent(this,MainActivityAcercade.class);
                startActivity(acercade);
                break;
        }
        return true;
    }

    //Cargo Listado

    public ArrayList<Model> dataqueue(String pBusqueda)
    {
        ArrayList<Model> holder=new ArrayList<>();


        //traigo last index
        traerLastIndex(pBusqueda);


        // comienzo loop I=1 hasta last
        for(i=0; i<auxindex; i++){
            Model ob1=new Model();


            //Traigo el evento
            traerEvento();


            ob1.setHeader(auxNom);
            ob1.setDesc(auxdsc);
            ob1.setImgname(R.drawable.cartel1);
            ob1.setFecha(auxfch);
            ob1.setOrganizador(auxorg);
            ob1.setTxtmapa(auxdir);
            ob1.setUrlimagen(auximg);
            //Agregado para compartir web
            ob1.setId(idEvento);

            ///Alternativa a cargar imagen/////
            //ob1.setImgen(imagen);
            ////////////////////////////////////
            holder.add(ob1);
            auxdir = null;
        }


        return holder;
    }



    //***********Subrutinas ****************//


    private void traerEvento(){
        //Se ejecuta en segundo plano

        Log.i("Eventos",String.valueOf(eventos.get(i).getNombreEvento()));
        auxNom = String.valueOf(eventos.get(i).getNombreEvento());

        Log.i("Eventos",String.valueOf(eventos.get(i).getDescripcion()));
        auxdsc = String.valueOf(eventos.get(i).getDescripcion());

        Log.i("Eventos",String.valueOf(eventos.get(i).getFechaInicio()));
        auxfch = String.valueOf(eventos.get(i).getFechaInicio());

        Log.i("Eventos",String.valueOf(eventos.get(i).getOrganizador()));
        auxorg = String.valueOf(eventos.get(i).getOrganizador());

        Log.i("Eventos",String.valueOf(eventos.get(i).getDireccion()));
        auxdir = String.valueOf(eventos.get(i).getDireccion());

        Log.i("Eventos",String.valueOf(eventos.get(i).getImagenEvento()));
        auximg = String.valueOf(eventos.get(i).getImagenEvento());
        //Agregado para compartir web
        idEvento = eventos.get(i).getId();

        ///Alternativa a cargar imagen/////
        //imagen=eventos.get(i).getImagenEventoBMP();
        //////////////////////////////////
    }

    private void traerLastIndex(String pBusqueda){
        //Se ejecuta antes de la tarea en segundo plano

        //new Thread(new Runnable() {
        // @Override
        //public void run() {
        //Se ejecuta en segundo plano

        EventoModel instance = EventoModel.getInstance();
        eventos = instance.obtenerEventosPorCategoriaONombre(pBusqueda);
        //Log.i("Eventos","holaa");

        Log.i("Eventos",String.valueOf(eventos.size()));
        //  auxIntstr = String.valueOf(eventoDTOS.size());
        auxindex = eventos.size();


        //Se ejecuta al terminar la tarea en segundo plano
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(getApplicationContext(),eventoDTOS.size(),Toast.LENGTH_LONG).show();
            }
        });
    }
    //}).start();

    // }

}