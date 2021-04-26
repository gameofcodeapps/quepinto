package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.provider.MediaStore;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IRegistrarEventoPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.RegistrarEventoPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class MainActivityRegEvento extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, View.OnClickListener {


    private GoogleMap mMap;
    Button bfecha,bhora;
    EditText efecha,ehora;
    private int dia,mes,ano,hora,minutos;
    ImageView imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reg_evento);

        EditText nombreEvent = findViewById(R.id.nombreEvento);
        EditText organizador = findViewById(R.id.organizador);
        EditText descrip = findViewById(R.id.descripcion);
        EditText departament = findViewById(R.id.departamento);
        EditText city = findViewById(R.id.ciudad);
        EditText address = findViewById(R.id.direccion);
        imagen = (ImageView)findViewById(R.id.imageView);

        Button Registrar = (Button)findViewById(R.id.btnModificarEvento);

        bfecha=(Button)findViewById(R.id.btnFecha);
        bhora=(Button)findViewById(R.id.btnHora);
        efecha=(EditText) findViewById(R.id.txtFecha);
        ehora=(EditText) findViewById(R.id.txtHora);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.spinnerModificarEvento);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 1;
                String evento = nombreEvent.getText().toString();
                String organiza = organizador.getText().toString();
                String categoria = spinner.getSelectedItem().toString();
                String descripcion = descrip.getText().toString();
                String Cargaimagen = imagen.getImageMatrix().toString();
                String ciudad = city.getText().toString();
                String departamento = departament.getText().toString();
                String pais = "Uruguay";
                String fecha = efecha.getText().toString();
                String hora = ehora.getText().toString();
                String latitud = "";
                String direccion = address.getText().toString();
                String longitud = "";
                String usuarioCreador = UsuarioModel.getInstance().usuarioLogeado.getUsername();

                EventoDTO nuevoEvento = new EventoDTO(id,evento,organiza,categoria,descripcion,Cargaimagen,ciudad,departamento,pais,fecha,"","",hora,latitud,direccion,longitud,usuarioCreador);
                Log.i("evento",nuevoEvento.getDepartamento());
                if(validarDatosEvent(nuevoEvento)){
                    ProgressDialog progressDialog = ProgressDialog.show(MainActivityRegEvento.this, "","Creando evento", true) ;
                    //////////////////////////////////////////////////////////////
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ////////Se ejecuta en segundo plano//////////////////////
                            IRegistrarEventoPresenter presenter = new RegistrarEventoPresenter();

                            int resultado = presenter.registrarEvento(nuevoEvento);
                            ////////////////////////////////////////////////////////
                            //Se ejecuta al terminar la tarea en segundo plano
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if (resultado == 0) {
                                        AlertDialog dialogo = new AlertDialog.Builder(MainActivityRegEvento.this).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent buscar = new Intent(MainActivityRegEvento.this,MainActivityBusEvento.class);
                                                startActivity(buscar);
                                            }
                                        })
                                        .setTitle("Creando Evento")
                                        .setMessage("El evento fue creado exitosamente")
                                        .create();
                                     dialogo.show();

                                    }else if(resultado == 1){
                                        Toast.makeText(getApplicationContext(),"Hubo un error al registrar el evento, intente nuevamente :)",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }).start();
                }

            }
        });

    }



    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(markerOptions);
            }
        });
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        LocationManager locationManager = (LocationManager) MainActivityRegEvento.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion,15));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(miUbicacion)
                        .zoom(20)
                        .bearing(90)
                        .tilt(45)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v==bfecha){
            final Calendar c = Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+month+"/"+year);

                }
            }
            ,ano,mes,dia);
            datePickerDialog.show();
        }
        if (v==bhora){
            final Calendar c = Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
    }

    private boolean validarDatosEvent(EventoDTO pEvento) {
        if (pEvento.getNombreEvento().isEmpty()) {
            Toast.makeText(getApplicationContext(), "El nombre del evento no puede estar vacío", Toast.LENGTH_LONG).show();
            return false;
        } else if (pEvento.getOrganizador().isEmpty()) {
            Toast.makeText(getApplicationContext(), "El nombre del organizador no puede estar vacío", Toast.LENGTH_LONG).show();
            return false;
        } else if (pEvento.getFechaInicio().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una fecha para el evento", Toast.LENGTH_LONG).show();
            return false;
        } else if (pEvento.getHoraInicio().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una hora para el evento", Toast.LENGTH_LONG).show();
            return false;
        }else if (pEvento.getDescripcion().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una descripcion", Toast.LENGTH_LONG).show();
            return false;
        }else if (pEvento.getDepartamento().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar un departamento", Toast.LENGTH_LONG).show();
            return false;
        }else if (pEvento.getCiudad().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una ciudad", Toast.LENGTH_LONG).show();
            return false;
        }else if (pEvento.getDireccion().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una direccion", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void cargarImagen(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la imagen"), 10);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}