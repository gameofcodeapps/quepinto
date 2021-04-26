package com.gameofcode.quepinto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.interfaces.IPerfilUsuarioPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.MainPresenter;
import com.gameofcode.quepinto.presentadores.PerfilUsuarioPresenter;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityPerfil extends AppCompatActivity {

   private EditText usuario;
   private EditText usuNom;
   private EditText usuApe;
   private EditText usuMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil);


        usuario = findViewById(R.id.Nom_Usuario_perf);
        usuNom = findViewById(R.id.UsuNom);
        usuApe = findViewById(R.id.Apellido);
        usuMail = findViewById(R.id.email);
        //EditText usuPasswd = findViewById(R.id.passwordUsuarioET);
        //EditText UsuValPwd = findViewById(R.id.repetirPasswordUsuarioET);

        Button btn_guard_camb = findViewById(R.id.button2);

        //El usuario no se puede modificar, pero se muestra
        usuario.setText(UsuarioModel.getInstance().getUsuarioLogeado().getUsername());
        usuario.setEnabled(false);
        //Datos que se pueden modificar
        usuNom.setText(UsuarioModel.getInstance().getUsuarioLogeado().getFirstName());
        usuApe.setText(UsuarioModel.getInstance().getUsuarioLogeado().getLastName());
        usuMail.setText(UsuarioModel.getInstance().getUsuarioLogeado().getEmail());


        Toolbar toolbar = findViewById(R.id.toolbar);
        //pongo Título
        toolbar.setTitle("Qué Pintó?");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);


    }

    public void actualizarDatos(View v){
        UsuarioModel.getInstance().getUsuarioLogeado().setFirstName(usuNom.getText().toString());
        UsuarioModel.getInstance().getUsuarioLogeado().setLastName(usuApe.getText().toString());
        UsuarioModel.getInstance().getUsuarioLogeado().setEmail(usuMail.getText().toString());
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Actualizando Datos...", true);
        /////////////////////////////////////////////////////////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                ////////Se ejecuta en segundo plano//////////////////////
                IPerfilUsuarioPresenter iMainPresenter = new PerfilUsuarioPresenter();
                boolean boolBsuarioValido = iMainPresenter.actulizarDatosUsuarioLogeado();
                ////////////////////////////////////////////////////////
                //Se ejecuta al terminar la tarea en segundo plano
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(boolBsuarioValido){
                            Intent buscar = new Intent(getApplicationContext(),MainActivityBusEvento.class);
                            startActivity(buscar);
                        }else{
                            Toast.makeText(getApplicationContext(),"Error al actualizar los datos, intente nuevamente.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                /////////////////////////////////////////////////////////
            }
        }).start();
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






