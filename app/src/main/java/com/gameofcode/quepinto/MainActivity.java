package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;
import com.gameofcode.quepinto.models.UsuarioModel;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this,"esto es solo un prueba, los stings van en el XML",Toast.LENGTH_LONG).show();
        doStaff();




    }

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
