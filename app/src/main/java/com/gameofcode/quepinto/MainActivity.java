package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;
import com.gameofcode.quepinto.models.UsuarioModel;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
    Button BtnEven;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this,"esto es solo un prueba, los stings van en el XML",Toast.LENGTH_LONG).show();
        doStaff();
        EditText UsuNom = findViewById(R.id.TxtUsuNom);
        EditText UsuPwd = findViewById(R.id.TxtUsuPas);
        BtnEven = findViewById(R.id.BtnEven);



    }
    public void buscarEventos(View view) {
        Intent buscar = new Intent(this,MainActivity2.class);
        startActivity(buscar);}

    public void noRegistro(View view) {
        Intent registro = new Intent(this,MainActivity3.class);
        startActivity(registro);}


    private void doStaff(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UsuarioModel usuarioModel = new UsuarioModel();
                UsuarioDTO usuarioDTO = usuarioModel.ObtenerDatosUsuario("admin", "admin");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(usuarioDTO.getUsername());
                        Toast.makeText(getApplicationContext(),usuarioDTO.getUsername(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        }).start();

    }

}
