package com.gameofcode.quepinto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityPerfil extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil);


        EditText Usu = findViewById(R.id.usuarioET);
        EditText UsuNom = findViewById(R.id.nombreUsuarioET);
        EditText UsuApe = findViewById(R.id.apellidoUsuarioET);
        EditText UsuMail = findViewById(R.id.emailUsuarioET);
        EditText UsuPwd = findViewById(R.id.passwordUsuarioET);
        EditText UsuValPwd = findViewById(R.id.repetirPasswordUsuarioET);

        Button btn_guard_camb = findViewById(R.id.button2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //pongo Título
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

        switch (item.getItemId()) {

            case R.id.menuF:
                Intent favoritos = new Intent(this,MainActivityFavoritos.class);
                startActivity(favoritos);
                break;

            case R.id.mencCr:
                Intent creevento = new Intent(this, MainActivityRegEvento.class);
                startActivity(creevento);
                break;
            case R.id.menuL:
                Intent logout = new Intent(this, MainActivity.class);
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

    ///////////// onclick boton cambios ///////////

    public void onClick_btn(View view) {

    }
}






