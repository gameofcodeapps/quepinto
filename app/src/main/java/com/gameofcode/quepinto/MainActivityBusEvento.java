package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.SearchView;


import com.bumptech.glide.Glide;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.PreferenciasSistema;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityBusEvento extends AppCompatActivity {

    RecyclerView rcv;
    int i,auxindex;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr;
    ImageView imageView;
    myadapter adapter;
    Bitmap bmp;
    private List<EventoDTO> eventos = null;
    private int idEvento;
    ////Alternativa cargar imagen/////////
    //private Bitmap imagen;
    //////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buscar_evento);
        //accedo a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
         rcv= findViewById(R.id.recview);

        //pongo Título
        toolbar.setTitle("Qué Pintó?");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

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
        Log.i("favoritos",String.valueOf(UsuarioModel.getInstance().getUsuarioLogeado().getEventoFavoritos().size()));
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

                //Se crea el Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
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
                Intent acercade = new Intent(this,MainActivityAcercade.class);
                startActivity(acercade);
                break;
            case R.id.menuM:
                Intent miseventos = new Intent(this,MainActivityMisEventosCreados.class);
                startActivity(miseventos);
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
                if(pBusqueda.isEmpty()){
                    eventos = instance.obtenerTodosLosEventosHabilitados();
                }else{
                    eventos = instance.obtenerEventosPorCategoriaONombre(pBusqueda);
                }

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



