package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gameofcode.quepinto.DTO.EventoDTO;

public class MainActivityModificarEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar_evento);
        EventoDTO evento = (EventoDTO) getIntent().getSerializableExtra("evento");
        EditText nombreEvento = (EditText) findViewById(R.id.nombreEvento);
        nombreEvento.setText(evento.getNombreEvento());

        Button botonModificar = (Button) findViewById(R.id.btnModificarEvento);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.setNombreEvento(nombreEvento.getText().toString());
            }
        });

    }
}