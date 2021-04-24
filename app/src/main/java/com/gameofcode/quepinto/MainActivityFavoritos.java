package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.models.EventoModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFavoritos extends AppCompatActivity {
    RecyclerView rcv;
    int i,auxindex;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr;
    ImageView imageView;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favoritos);
        //accedo a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        rcv= findViewById(R.id.recview);

        //pongo Título
        toolbar.setTitle("Qué Pintó?");

        adapter = new myadapter(dataqueue(),getApplicationContext());
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rcv.setLayoutManager(gridLayoutManager);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
    }
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
                Intent favoritos = new Intent(this,MainActivityBusEvento.class);
                startActivity(favoritos);
                break;

            case R.id.mencCr:
                Intent creevento = new Intent(this,MainActivityRegEvento.class);
                startActivity(creevento);
                break;
            case R.id.menuL:
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
        //auxindex= 12;

        for(i=0; i<auxindex; i++){
            Model ob1=new Model();
            traerEvento();

            while(auxdir == null){

            }
            ob1.setHeader(auxNom);
            ob1.setDesc(auxdsc);
            ob1.setImgname(R.drawable.banda1);
            ob1.setFecha(auxfch);
            ob1.setOrganizador(auxorg);
            ob1.setTxtmapa(auxdir);

            holder.add(ob1);
            auxdir = null;
        }


        return holder;
    }
//fin loop
    /*    Model ob2=new Model();
        ob2.setHeader(auxNom);
        ob2.setDesc(auxdsc);
        ob2.setImgname(R.drawable.toque);
        ob2.setFecha(auxfch);
        ob2.setOrganizador(auxorg);
        ob2.setTxtmapa(auxdir);

        holder.add(ob2);

        Model ob3=new Model();
        ob3.setHeader(auxNom);
        ob3.setDesc(auxdsc);
        ob3.setImgname(R.drawable.banda3);
        ob3.setFecha(auxfch);
        ob3.setOrganizador(auxorg);
   //     ob1.setTxtmapa( "busque aquí");

        holder.add(ob3);*/






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

        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();


    }
}