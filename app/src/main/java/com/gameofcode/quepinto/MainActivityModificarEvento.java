package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gameofcode.quepinto.DTO.EventoDTO;

public class MainActivityModificarEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar_evento);
        EventoDTO evento = (EventoDTO) getIntent().getSerializableExtra("evento");
        EditText nombreEvento = (EditText) findViewById(R.id.nombreEvento);
        EditText organizador = (EditText) findViewById(R.id.organizador);
        Spinner categorias = (Spinner) findViewById(R.id.spinner);
        EditText fecha = (EditText) findViewById(R.id.txtFecha);
        EditText hora = (EditText) findViewById(R.id.txtHora);
        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        EditText departamento = (EditText) findViewById(R.id.departamento);
        EditText ciudad = (EditText) findViewById(R.id.ciudad);
        EditText direccion = (EditText) findViewById(R.id.direccion);
        nombreEvento.setText(evento.getNombreEvento());

        organizador.setText(evento.getOrganizador());
        fecha.setText(evento.getFechaInicio());
        hora.setText(evento.getHoraInicio());
        descripcion.setText(evento.getDescripcion());
        departamento.setText(evento.getDepartamento());
        ciudad.setText(evento.getCiudad());
        direccion.setText(evento.getDireccion());



        Button botonModificar = (Button) findViewById(R.id.btnModificarEvento);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.setNombreEvento(nombreEvento.getText().toString());
            }
        });

    }
}