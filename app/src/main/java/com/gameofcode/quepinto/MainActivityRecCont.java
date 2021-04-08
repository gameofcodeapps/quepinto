package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityRecCont extends AppCompatActivity {
    Button btnEnviar;
    Button btnVolVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rec_contrasena);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVolVer = findViewById(R.id.btnVolver);
    }
    public void regresar(View view) {
        Intent volver = new Intent(this,MainActivity.class);
        startActivity(volver);}
}