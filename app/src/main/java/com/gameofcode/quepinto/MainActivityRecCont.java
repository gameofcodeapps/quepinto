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

public class MainActivityRecCont extends AppCompatActivity {
    Button btnEnviar;
    Button btnVolVer;
    Button btnValidar;
    EditText etCodigo;
    EditText etMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rec_contrasena);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVolVer = findViewById(R.id.btnCambiarPassword);
        btnValidar = findViewById(R.id.btnValidar);
        etMail = findViewById(R.id.etMail);
        etCodigo = findViewById(R.id.etValidarCodigo);
        btnValidar.setVisibility(View.INVISIBLE);
        etCodigo.setVisibility(View.INVISIBLE);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IResetearPasswordPresenter resetearPasswordPresenter = ResetearPasswordPresenter.getInstance();
                if(etCodigo.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(),"No has ingresado un codigo.", Toast.LENGTH_LONG).show();
                }
                if (resetearPasswordPresenter.validarCodigo(etCodigo.getText().toString())){
                    Intent cambioClave = new Intent(getApplicationContext(),CrearNuevaPassword.class);
                    startActivity(cambioClave);
                }else{
                    Toast.makeText(v.getContext(),"El codigo ingresado no es correcto", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IResetearPasswordPresenter resetearPasswordPresenter =  ResetearPasswordPresenter.getInstance();
                if (etMail.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(),"Debe ingresar un correo",Toast.LENGTH_LONG).show();
                }else{
                    ProgressDialog progressDialog= ProgressDialog.show(v.getContext(), "",
                            "Validando Datos y enviando correo de validacion...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int resultado = resetearPasswordPresenter.enviarMailCodigoConfirmacion(etMail.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if(resultado==0){

                                        btnValidar.setVisibility(View.VISIBLE);
                                        etCodigo.setVisibility(View.VISIBLE);
                                        AlertDialog dialogo = new AlertDialog.Builder(v.getContext()).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                                .setTitle("Aviso!")
                                                .setMessage("Se ha enviado un correo con el codigo de validación")
                                                .create();
                                        dialogo.show();
                                    }else if(resultado == 1){
                                        Toast.makeText(v.getContext(),"Hubo un error al enviar el correo",Toast.LENGTH_LONG).show();
                                    }else if(resultado == 2){
                                        Toast.makeText(v.getContext(),"El correo no esta registrado",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }).start();

                }

            }
        });
    }

    public void regresar(View view) {
        Intent volver = new Intent(this,MainActivity.class);
        startActivity(volver);
    }
}