package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.gameofcode.quepinto.DTO.ComentarioDTO;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.broadcast_reciver.BootReceiver;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;
import com.gameofcode.quepinto.helpers.PreferenciasSistema;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.models.UsuarioModel;
import com.gameofcode.quepinto.presentadores.MainPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button BtnEven;
    SignInButton signin;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //doStaff();
        EditText UsuNom = findViewById(R.id.TxtUsuNom);
        EditText UsuPwd = findViewById(R.id.TxtUsuPas);
        BtnEven = findViewById(R.id.BtnEven);
        signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        IMainPresenter iMainPresenter = new MainPresenter();
        //Si existe usuario grabado inicia sesion automaticamente
        if(iMainPresenter.existeUsuarioGrabado(getApplicationContext())){
            //logUsuario(null);
        }

       // PreferenciasSistema.getInstance().obtener().getString("nombre","lalala");
       // SharedPreferences preferenciasSistema = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
       // preferenciasSistema.getString("nombre","lalala");

        PreferenciasSistema.leerPreferencia(getApplicationContext(),"name");

    }
    public void buscarEventos(View view) {
        Intent buscar = new Intent(this,BuscarsinLogueo.class);
        startActivity(buscar);}

    public void noRegistro(View view) {
        Intent registro = new Intent(this,MainActivityRegUsuario.class);
        startActivity(registro);}

    public void olvidoCont(View view) {
        Intent recCon = new Intent(this,MainActivityRecCont.class);
        startActivity(recCon);}

    public void logUsuario(View view){

        //Se ejecuta antes de la tarea en segundo plano//////////////////
        EditText etUsuario = findViewById(R.id.TxtUsuNom);
        EditText etPassword = findViewById(R.id.TxtUsuPas);
        ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Validando Usuario...", true);
        /////////////////////////////////////////////////////////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                ////////Se ejecuta en segundo plano//////////////////////
                IMainPresenter iMainPresenter = new MainPresenter();
                boolean boolBsuarioValido = iMainPresenter.validarUsuario(etUsuario.getText().toString(), etPassword.getText().toString(),getApplicationContext());
                ////////////////////////////////////////////////////////
                //Se ejecuta al terminar la tarea en segundo plano
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(boolBsuarioValido){
                            Intent buscar = new Intent(getApplicationContext(),MainActivityBusEvento.class);
                            startActivity(buscar);
                            }else{
                            Toast.makeText(getApplicationContext(),"Usuario o clave incorrecta",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                /////////////////////////////////////////////////////////
            }
        }).start();

    }
    public void doStaff(View view){
        //BootReceiver.scheduleJob(getApplicationContext());
        //Log.i("md5", UsuarioModel.getInstance().encriptarMD5("pepe1234"));

        //Se ejecuta antes de la tarea en segundo plano

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Se ejecuta en segundo plano

                EventoModel instance = EventoModel.getInstance();
                EventoDTO test = new EventoDTO();
                test.setId(17);
                List<ComentarioDTO> comentarioDTOS = instance.obtenerComentariosDeEvento(test);
                Log.i("Comentario",comentarioDTOS.get(0).getUsuario());
                //List<EventoDTO> eventoDTOS = instance.obtenerEventosFavoritosServicio("juan1");
                //EventoDTO evento = new EventoDTO(
                //    1,"evento Prueba","yo","clande","re piola","imagen","maldonado","maldonado","uruguay","2021-04-25","","","20:00","548484984","la direccion","51651651561651", "juan1");
                //instance.agregarEvento(evento);
               // List<EventoDTO> eventoDTOS = instance.obtenerTodosLosEventosHabilitados();
               // EventoDTO eventoDTO = eventoDTOS.get(12);
                //eventoDTO.setNombreEvento("Hackaton 2021 modificado");
                //instance.actualizarDatosEvento(eventoDTO);

                //List<EventoDTO> hack = instance.obtenerEventosPorCategoriaONombre("hack");
                //Log.i("Eventos",String.valueOf(hack.size()));
                //Log.i("Eventos",String.valueOf(eventoDTOS.get(1).getImagenEvento()));

                //Crear Usuario
              //  UsuarioDTO u = new UsuarioDTO("Demo4.123","demo4","demo","demo@demo.com","Demo");
                //int resultado = UsuarioModel.getInstance().crearUsuario(u);


                //Agregar Comentario
                //EventoDTO evento = eventoDTOS.get(1);
                //Log.i("evento ID",String.valueOf(evento.getId()));
                //EventoModel.getInstance().agregarComentario(evento,"Esto es un comentario");

                //Se ejecuta al terminar la tarea en segundo plano
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       // Toast.makeText(getApplicationContext(),String.valueOf(resultado),Toast.LENGTH_LONG).show();

                    }
                });
            }
        }).start();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(MainActivity.this,MainActivityBusEvento.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}
