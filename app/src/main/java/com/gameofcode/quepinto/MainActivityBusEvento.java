package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivityBusEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buscar_evento);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Qué Pintó?");

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
}