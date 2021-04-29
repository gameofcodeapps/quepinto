package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gameofcode.quepinto.interfaces.IResetearPasswordPresenter;
import com.gameofcode.quepinto.presentadores.ResetearPasswordPresenter;

public class CrearNuevaPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nueva_password);
        EditText etPassword = findViewById(R.id.etNuevaPass);
        EditText etConfPassword = findViewById(R.id.etConfirmarPass);
        Button btnConfirmar = findViewById(R.id.btnCambiarPassword);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarClave(etPassword.getText().toString(),etConfPassword.getText().toString())){
                    ProgressDialog progressDialog= ProgressDialog.show(v.getContext(), "",
                            "Cambiando clave...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            IResetearPasswordPresenter instance = ResetearPasswordPresenter.getInstance();
                            boolean resultado = instance.resetearPassword(etPassword.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (resultado){
                                        AlertDialog dialogo = new AlertDialog.Builder(v.getContext()).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent inicio = new Intent(v.getContext(),MainActivity.class);
                                                startActivity(inicio);
                                            }
                                        })
                                                .setTitle("")
                                                .setMessage("Se ha cambiado la clave con exito!")
                                                .create();
                                        dialogo.show();
                                    }else{
                                        Toast.makeText(v.getContext(),"Error al actulizar la clave, intente nuevamente",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    }).start();
                }
            }
        });

    }

    private boolean validarClave(String pPassword,String pConfirmar){
        IResetearPasswordPresenter resetearPasswordPresenter = ResetearPasswordPresenter.getInstance();
        if(!pPassword.matches(".{8,100}")){
            Toast.makeText(getApplicationContext(),"La contraseña no contiene un minimo de 8 caracteres",Toast.LENGTH_LONG).show();
            return false;
        }else if(pPassword.matches("[^a-z ]\\ *([.0-9])*\\d")){
            Toast.makeText(getApplicationContext(),"La contraseña es solo numerica",Toast.LENGTH_LONG).show();
            return false;
        }else if(pPassword.contains(resetearPasswordPresenter.getUsuario().getUsername())){
            Toast.makeText(getApplicationContext(),"La contraseña contiene el usuario",Toast.LENGTH_LONG).show();
            return false;
        }else if(!(pPassword.equals(pConfirmar))){
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}