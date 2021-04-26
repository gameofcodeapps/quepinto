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

public class MainActivityMisEventosCreados extends AppCompatActivity {

    RecyclerView rcv;
    int i,auxindex;
    String auxNom,auxdsc,auxfch,auxorg,auxdir,auximg,auxIntstr;
    ImageView imageView;
    myadapter_editarEventoCreado adapter;
    Bitmap bmp;
    private List<EventoDTO> eventos = null;
    private int idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_miseventoscreados);
        //accedo a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        rcv= findViewById(R.id.recview);

        //pongo Título
        toolbar.setTitle("Qué Pintó?");

        inicializar();

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);




    }

    private void inicializar(){
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Cargando Mis Eventos...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventoModel instance = EventoModel.getInstance();
                eventos = instance.obtenerEventosCreadosPorUsuarioLogeado();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new myadapter_editarEventoCreado(eventos,getApplicationContext());
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
                PreferenciasSistema.agregarPreferencia(getApplicationContext(),"usuario","");
                PreferenciasSistema.agregarPreferencia(getApplicationContext(),"password","pPassword");
                Intent logout = new Intent(this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.menuA:
                Intent acercade = new Intent(this,MainActivityAcercade.class);
                startActivity(acercade);
                break;

        }
        return true;
    }

}