package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IModificarEventoPresenter;
import com.gameofcode.quepinto.presentadores.ModificarEventoPresenter;

public class MainActivityModificarEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar_evento);

        Spinner categorias = findViewById(R.id.spinnerModificarEvento);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);


        EventoDTO evento = (EventoDTO) getIntent().getSerializableExtra("evento");
        EditText nombreEvento = (EditText) findViewById(R.id.nombreEvento);
        EditText organizador = (EditText) findViewById(R.id.organizador);
        EditText fecha = (EditText) findViewById(R.id.txtFecha);
        EditText hora = (EditText) findViewById(R.id.txtHora);
        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        EditText departamento = (EditText) findViewById(R.id.departamento);
        EditText ciudad = (EditText) findViewById(R.id.ciudad);
        EditText direccion = (EditText) findViewById(R.id.direccion);
        nombreEvento.setText(evento.getNombreEvento());

        organizador.setText(evento.getOrganizador());
        fecha.setText(evento.getFechaInicio().substring(0,10));
        hora.setText(evento.getHoraInicio());
        descripcion.setText(evento.getDescripcion());
        departamento.setText(evento.getDepartamento());
        ciudad.setText(evento.getCiudad());
        direccion.setText(evento.getDireccion());

        if(evento.getCategoria().equals("Teatro")){
            categorias.setSelection(0);
        }else if(evento.getCategoria().equals("Restaurant")){
            categorias.setSelection(1);
        }else if(evento.getCategoria().equals("Festival")){
            categorias.setSelection(2);
        }else if(evento.getCategoria().equals("Evento Deportivo")){
            categorias.setSelection(3);
        }else if(evento.getCategoria().equals("Concierto")){
            categorias.setSelection(4);
        }else if(evento.getCategoria().equals("Cine")){
            categorias.setSelection(5);
        }




        Button botonModificar = (Button) findViewById(R.id.btnModificarEvento);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.setNombreEvento(nombreEvento.getText().toString());
                evento.setOrganizador(organizador.getText().toString());
                evento.setFechaInicio(fecha.getText().toString());
                evento.setHoraInicio(hora.getText().toString());
                evento.setDescripcion(descripcion.getText().toString());
                evento.setDepartamento(departamento.getText().toString());
                evento.setCiudad(ciudad.getText().toString());
                evento.setDireccion(direccion.getText().toString());
                ProgressDialog progressDialog= ProgressDialog.show(v.getContext(), "",
                        "Modificando Evento...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IModificarEventoPresenter modificarEventoPresenter = new ModificarEventoPresenter();
                        boolean resultado = modificarEventoPresenter.modificarEvento(evento);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if(resultado){
                                    Toast.makeText(getApplicationContext(),"Listo!",Toast.LENGTH_LONG);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setMessage("El evento ha sido modificado!")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent busEvento = new Intent(v.getContext(),MainActivityBusEvento.class);
                                                    startActivity(busEvento);
                                                }
                                            });
                                    builder.create();
                                    builder.show();

                                }else{
                                    Toast.makeText(getApplicationContext(),"Error al ingresar el comentario",Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

    }
}