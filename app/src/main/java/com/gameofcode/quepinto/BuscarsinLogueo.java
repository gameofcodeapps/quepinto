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

       inicializar();

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar2);

    }

    private void inicializar(){
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Buscando Eventos...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Model> holder = dataqueue();
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
        }
        return true;
    }

    public ArrayList<Model> dataqueue()
    {
        ArrayList<Model> holder=new ArrayList<>();


    //traigo last index
        traerLastIndex();

        while (auxindex == 0){
        }

        // comienzo loop I=1 hasta last
        for(i=0; i<auxindex; i++){
            Model ob1=new Model();
            traerEvento();

            while(auxdir == null){
                }

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



    private void traerEvento(){
        //Se ejecuta antes de la tarea en segundo plano

        new Thread(new Runnable() {
            @Override
           public void run() {
                //Se ejecuta en segundo plano

                EventoModel instance = EventoModel.getInstance();
                List<EventoDTO> eventoDTOS = instance.obtenerTodosLosEventosHabilitados();
                //Log.i("Eventos","holaa");

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getNombreEvento()));
                auxNom = String.valueOf(eventoDTOS.get(i).getNombreEvento());

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getDescripcion()));
                auxdsc = String.valueOf(eventoDTOS.get(i).getDescripcion());

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getFechaInicio()));
                auxfch = String.valueOf(eventoDTOS.get(i).getFechaInicio());

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getOrganizador()));
                auxorg = String.valueOf(eventoDTOS.get(i).getOrganizador());

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getDireccion()));
                auxdir = String.valueOf(eventoDTOS.get(i).getDireccion());

                Log.i("Eventos",String.valueOf(eventoDTOS.get(i).getImagenEvento()));
                auximg = String.valueOf(eventoDTOS.get(i).getImagenEvento());
                idEvento = eventoDTOS.get(i).getId();


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
        }).start();


    }
    private void traerLastIndex(){
        //Se ejecuta antes de la tarea en segundo plano

        //new Thread(new Runnable() {
          //  @Override
           // public void run() {
                //Se ejecuta en segundo plano

                EventoModel instance = EventoModel.getInstance();
                List<EventoDTO> eventoDTOS = instance.obtenerTodosLosEventosHabilitados();
                //Log.i("Eventos","holaa");

                Log.i("Eventos",String.valueOf(eventoDTOS.size()));
              //  auxIntstr = String.valueOf(eventoDTOS.size());
                auxindex = eventoDTOS.size();


                //Se ejecuta al terminar la tarea en segundo plano
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(),eventoDTOS.size(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        //}).start();


    //}
}