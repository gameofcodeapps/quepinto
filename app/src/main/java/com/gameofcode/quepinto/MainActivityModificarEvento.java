package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    }
}