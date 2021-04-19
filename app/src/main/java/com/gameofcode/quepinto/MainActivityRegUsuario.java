package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.interfaces.IRegistrarUsuarioPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.MainPresenter;
import com.gameofcode.quepinto.presentadores.RegistrarUsuarioPresenter;

import java.util.regex.Pattern;

public class MainActivityRegUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main_reg_usuario);

        EditText Usu = findViewById(R.id.usuarioET);
        EditText UsuNom = findViewById(R.id.nombreUsuarioET);
        EditText UsuApe = findViewById(R.id.apellidoUsuarioET);
        EditText UsuMail = findViewById(R.id.emailUsuarioET);
        EditText UsuPwd = findViewById(R.id.passwordUsuarioET);
        EditText UsuValPwd = findViewById(R.id.repetirPasswordUsuarioET);

        Button Enviar = (Button)findViewById(R.id.but_Env);

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = Usu.getText().toString();
                String nombre = UsuNom.getText().toString();
                String Apellido = UsuApe.getText().toString();
                String Mail = UsuMail.getText().toString();
                String Password = UsuPwd.getText().toString();
                String ConfPass = UsuValPwd.getText().toString();
                UsuarioDTO nuevoUsuario = new UsuarioDTO(Password,usuario,nombre,Mail,Apellido);
                if(validarDatosIngresados(nuevoUsuario,ConfPass)){

                    ProgressDialog progressDialog= ProgressDialog.show(MainActivityRegUsuario.this, "",
                            "Creando usuario...", true);
                    /////////////////////////////////////////////////////////////////
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ////////Se ejecuta en segundo plano//////////////////////
                            IRegistrarUsuarioPresenter presenter = new RegistrarUsuarioPresenter();
                            //Valores retorno
                            /*
                            0 = nombre de usuario y mail no registrado
                            1 = email registrado
                            2 = usuario registrado
                            3 = nombre de usuario y mail registrado
                             */
                            int resultado = presenter.crearUsuario(nuevoUsuario);
                            ////////////////////////////////////////////////////////
                            //Se ejecuta al terminar la tarea en segundo plano
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if(resultado == 0){
                                        AlertDialog dialogo = new AlertDialog
                                                .Builder(MainActivityRegUsuario.this)
                                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent buscar = new Intent(MainActivityRegUsuario.this,MainActivity.class);
                                                        startActivity(buscar);
                                                    }
                                                })
                                                .setTitle("Usuario Creado")
                                                .setMessage("El usuario fue creado exitosamente")
                                                .create();
                                        dialogo.show();
                                    }else if(resultado == 1){
                                        Toast.makeText(getApplicationContext(),"El email ya esta registrado",Toast.LENGTH_LONG).show();
                                    }else if(resultado == 2){
                                        Toast.makeText(getApplicationContext(),"El usuario ya esta registrado",Toast.LENGTH_LONG).show();
                                    }else if(resultado ==3){
                                        Toast.makeText(getApplicationContext(),"El usuario y el email ya esta registrado. Te olvidaste de la clave?",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            /////////////////////////////////////////////////////////
                        }
                    }).start();


                    //UsuarioModel.getInstance().crearUsuario(nuevoUsuario);

                }
            }
    });
}

    private boolean validarDatosIngresados(UsuarioDTO pUsuario,String pConfirmarPass){

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(pUsuario.getUsername().isEmpty()){
            Toast.makeText(getApplicationContext(),"El usuario no puede estar vacio",Toast.LENGTH_LONG).show();
            return false;
        }else if(pUsuario.getFirstName().isEmpty()){
            Toast.makeText(getApplicationContext(),"El nombre no puede estar vacio",Toast.LENGTH_LONG).show();
            return false;
        }else if(pUsuario.getLastName().isEmpty()){
            Toast.makeText(getApplicationContext(),"El apellido no puede estar vacio",Toast.LENGTH_LONG).show();
            return false;
        }else if(pUsuario.getEmail().isEmpty()){
            Toast.makeText(getApplicationContext(),"El email no puede estar vacio",Toast.LENGTH_LONG).show();
            return false;
        }else if(!pattern.matcher(pUsuario.getEmail()).matches()){
            Toast.makeText(getApplicationContext(),"El email ingresado no es valido",Toast.LENGTH_LONG).show();
            return false;
        }else if(!pUsuario.getEmail().matches(".*[!@#$%^&*+=?-].*")){
            Toast.makeText(getApplicationContext(),"La contraseña no contiene caracteres especiales",Toast.LENGTH_LONG).show();
            return false;
        }else if(!pUsuario.getPassword().matches(".{8,100}")){
            Toast.makeText(getApplicationContext(),"La contraseña no contiene un minimo de 8 caracteres",Toast.LENGTH_LONG).show();
            return false;
        }else if(pUsuario.getPassword().matches("[^a-z ]\\ *([.0-9])*\\d")){
            Toast.makeText(getApplicationContext(),"La contraseña es solo numerica",Toast.LENGTH_LONG).show();
            return false;
        }else if(pUsuario.getPassword().contains(pUsuario.getUsername())){
            Toast.makeText(getApplicationContext(),"La contraseña contiene el usuario",Toast.LENGTH_LONG).show();
            return false;
        }else if(!(pUsuario.getPassword().equals(pConfirmarPass))){
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}

