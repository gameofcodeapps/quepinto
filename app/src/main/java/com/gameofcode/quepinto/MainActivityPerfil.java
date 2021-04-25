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

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityPerfil extends AppCompatActivity {


    CircleImageView imagen;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil);

        imagen = findViewById(R.id.imagen_id);


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

        }
        return true;
    }

    public void cam_foto(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("imagen/");
        startActivityForResult(intent.createChooser(intent, "Selecione la Aplicación"), 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}








