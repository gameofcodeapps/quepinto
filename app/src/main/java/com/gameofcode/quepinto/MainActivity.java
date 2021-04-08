package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.MainPresenter;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
    Button BtnEven;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doStaff();
        EditText UsuNom = findViewById(R.id.TxtUsuNom);
        EditText UsuPwd = findViewById(R.id.TxtUsuPas);
        BtnEven = findViewById(R.id.BtnEven);



    }
    public void buscarEventos(View view) {
        Intent buscar = new Intent(this,MainActivityBusEvento.class);
        startActivity(buscar);}

    public void noRegistro(View view) {
        Intent registro = new Intent(this,MainActivityRegEvento.class);
        startActivity(registro);}

    public void olvidoCont(View view) {
        Intent recCon = new Intent(this,MainActivityRecCont.class);
        startActivity(recCon);}

    public void logUsuario(View view){
        EditText etUsuario = findViewById(R.id.TxtUsuNom);
        EditText etPassword = findViewById(R.id.TxtUsuPas);
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Validando Usuario...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                IMainPresenter iMainPresenter = new MainPresenter();
                boolean boolBsuarioValido = iMainPresenter.validarUsuario(etUsuario.getText().toString(), etPassword.getText().toString());
                //Toast.makeText(getApplicationContext(),usuarioDTO.getUsername(),Toast.LENGTH_LONG).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(boolBsuarioValido){
                            progressDialog.dismiss();
                            Intent buscar = new Intent(getApplicationContext(),MainActivityBusEvento.class);
                            startActivity(buscar);
                            }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Usuario o clave incorrecta",Toast.LENGTH_LONG).show();

                        }


                    }
                });
            }

        }).start();

    }

    private void doStaff(){


    }

}
